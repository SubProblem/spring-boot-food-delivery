package com.subproblem.restaurantservice.service

import com.subproblem.restaurantservice.dto.request.RestaurantRequestDTO
import com.subproblem.restaurantservice.dto.response.RestaurantResponseDTO
import com.subproblem.restaurantservice.entity.Restaurant
import com.subproblem.restaurantservice.repository.MenuRepository
import com.subproblem.restaurantservice.repository.RestaurantRepository
import com.subproblem.restaurantservice.util.RequestMapper
import com.subproblem.restaurantservice.util.ResponseMapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.function.ServerResponse.async
import java.time.Duration
import java.time.Instant

@Service
class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val menuRepository: MenuRepository,
    private val requestMapper: RequestMapper,
    private val responseMapper: ResponseMapper
) {

    fun getRestaurants(): ResponseEntity<List<RestaurantResponseDTO>> {

        val restaurants = restaurantRepository.findAll()
        val restaurantResponseToList = responseMapper.restaurantResponseToList(restaurants)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(restaurantResponseToList)
    }

    fun getRestaurantsWithMenus(): ResponseEntity<List<RestaurantResponseDTO>> {

        val menus = menuRepository.findAllWithMenuItemsAsync()
        val restaurants = restaurantRepository.findAllWithMenus()

        restaurants.map { restaurant ->
            val filteredMenus = menus.filter { it.restaurant.id == restaurant.id }
            restaurant.apply { this.menus.addAll(filteredMenus) }
        }

        val restaurantResponseToList = responseMapper.restaurantResponseToList(restaurants)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(restaurantResponseToList)
    }

    fun findRestaurantByName(name: String): ResponseEntity<RestaurantResponseDTO> {

        val restaurant = restaurantRepository.findByName(name)
            ?: throw IllegalArgumentException("Restaurant not found for name: $name")

        val response = responseMapper.toRestaurantResponse(restaurant)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }


    fun findByPhone(phone: String): ResponseEntity<RestaurantResponseDTO> {
        val restaurant = restaurantRepository.findByPhone(phone)
            ?: throw IllegalArgumentException("Restaurant not found for phone: $phone")

        val response = responseMapper.toRestaurantResponse(restaurant)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }

    fun findByAddress(keyword: String): ResponseEntity<List<RestaurantResponseDTO>> {
        val restaurants = restaurantRepository.findByAddressContaining(keyword)

        val response = responseMapper.restaurantResponseToList(restaurants)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }

    fun findByEmail(email: String): ResponseEntity<RestaurantResponseDTO> {
        val restaurant = restaurantRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Restaurant not found for email: $email")

        val response = responseMapper.toRestaurantResponse(restaurant)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }


    fun addRestaurant(request: RestaurantRequestDTO): ResponseEntity<HttpStatus> {
        val restaurant = requestMapper.requestToRestaurant(request)
        restaurantRepository.save(restaurant)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    fun findRestaurantsByIds(ids: List<Int>): ResponseEntity<List<RestaurantResponseDTO>> {
        val restaurants = restaurantRepository.findByIds(ids)
        val response = responseMapper.restaurantResponseToList(restaurants)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }
}