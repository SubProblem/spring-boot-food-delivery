package com.subproblem.orderservice.service

import com.subproblem.orderservice.dto.MenuItem
import com.subproblem.orderservice.dto.Restaurant
import com.subproblem.orderservice.dto.request.OrderRequestDTO
import com.subproblem.orderservice.dto.response.OrderResponseDTO
import com.subproblem.orderservice.entity.OrderStatus
import com.subproblem.orderservice.repository.OrderItemRepository
import com.subproblem.orderservice.repository.OrderRepository
import com.subproblem.orderservice.util.RequestMapper
import com.subproblem.orderservice.util.ResponseMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import java.math.BigDecimal
import kotlinx.coroutines.*
import kotlinx.coroutines.reactor.awaitSingleOrNull

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val responseMapper: ResponseMapper,
    private val requestMapper: RequestMapper,
    private val webClient: WebClient.Builder
) {


    fun getAllOrdersForUser(userId: Int): ResponseEntity<List<OrderResponseDTO>> {

        val orders = orderRepository.findByUserId(userId)
        val response = responseMapper.orderResponseDTOtoList(orders)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }

    fun getTotalAmountSpentByUser(userId: Int): ResponseEntity<BigDecimal> {

        val amount = orderRepository.getTotalAmountSpentByUser(userId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(amount)
    }


    fun getOrdersByStatus(userId: Int, status: String): ResponseEntity<List<OrderResponseDTO>> {
        val orders = orderRepository.findByUserIdAndStatus(userId, OrderStatus.valueOf(status))

        val response = responseMapper.orderResponseDTOtoList(orders)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }

    @Transactional
    fun makeOrder(userId: Int, request: OrderRequestDTO): ResponseEntity<OrderResponseDTO> {


        val order = requestMapper.requestToOrder(request).apply {
            this.userId = userId
        }

        orderRepository.save(order)
        val response = responseMapper.orderToResponseDTO(order)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response)

    }

    @Transactional
    suspend fun getOrdersWithRestaurantsAndMenuItems(userId: Int): ResponseEntity<Any> = coroutineScope {

        val orders = orderRepository.findByUserId(userId)

        val restaurantIdsDeferred = async {
            orders.map { it.restaurantId }.distinct()
        }

        val orderIdsDeferred = async {
            orders.flatMap { order ->
                order.orderItems.map {
                    it.menuItemId
                }.distinct()
            }
        }

        val allOrderItemsDeferred = async {
            orders.flatMap { it.orderItems }
        }

        val restaurantIds = restaurantIdsDeferred.await()
        val orderIds = orderIdsDeferred.await()
        val allOrderItems = allOrderItemsDeferred.await()


        val restaurantsDeferred = async {
            webClient.build()
                .get()
                .uri(
                    "lb://RESTAURANT-SERVICE/api/v1/restaurants/byIds?ids={restaurantIds}",
                    restaurantIds.joinToString(",")
                )
                .retrieve()
                .toEntityList(Restaurant::class.java)
                .awaitSingleOrNull()?.body
        }

        val menuItemsDeferred = async {
            webClient.build()
                .get()
                .uri("lb://RESTAURANT-SERVICE/api/v1/menus/byIds?ids={orderIds}", orderIds.joinToString(","))
                .retrieve()
                .toEntityList(MenuItem::class.java)
                .awaitSingleOrNull()?.body
        }

        val restaurants = restaurantsDeferred.await().orEmpty()
        val menuItems = menuItemsDeferred.await().orEmpty()


        val response = responseMapper.toOrderResponseWithRestaurantsList(
            orders,
            restaurants,
            allOrderItems,
            menuItems
        )

        return@coroutineScope ResponseEntity
            .status(HttpStatus.OK)
            .body(response)

    }


}