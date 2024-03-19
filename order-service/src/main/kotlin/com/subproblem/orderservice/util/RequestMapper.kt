package com.subproblem.orderservice.util

import com.subproblem.orderservice.dto.request.OrderItemRequestDTO
import com.subproblem.orderservice.dto.request.OrderRequestDTO
import com.subproblem.orderservice.entity.Order
import com.subproblem.orderservice.entity.OrderItem
import com.subproblem.orderservice.entity.OrderStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RequestMapper {

    fun requestToOrderItem(request: OrderItemRequestDTO, order: Order): OrderItem {
        return OrderItem().apply {
            this.order = order
            menuItemId = request.menuItemId
            quantity = request.quantity
            unitPrice = request.unitPrice
        }
    }

    fun requestToOrder(request: OrderRequestDTO): Order {
        val order =  Order().apply {
            restaurantId = request.restaurantId
            status = OrderStatus.valueOf(request.status)
            totalAmount = request.totalAmount
            createdAt = LocalDateTime.now()
        }

        val orderItems = orderItemRequestToOrderItemList(request.orderItems, order)
        order.orderItems.addAll(orderItems)

        return order
    }

    fun orderItemRequestToOrderItemList(items: List<OrderItemRequestDTO>, order: Order): List<OrderItem> {
        return items.map {
            OrderItem().apply {
                this.order = order
                menuItemId = it.menuItemId
                quantity = it.quantity
                unitPrice = it.unitPrice
            }
        }
    }
}