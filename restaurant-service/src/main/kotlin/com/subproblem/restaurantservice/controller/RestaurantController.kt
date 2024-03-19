package com.subproblem.restaurantservice.controller

import com.subproblem.restaurantservice.dto.request.RestaurantRequestDTO
import com.subproblem.restaurantservice.dto.response.RestaurantResponseDTO
import com.subproblem.restaurantservice.service.RestaurantService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/restaurants")
class RestaurantController(private val restaurantService: RestaurantService) {


    @GetMapping
    fun getAllRestaurants(): ResponseEntity<List<RestaurantResponseDTO>> =
        restaurantService.getRestaurants()

    @GetMapping("/menus")
    fun getAllRestaurantsWithMenus(): ResponseEntity<List<RestaurantResponseDTO>> =
        restaurantService.getRestaurantsWithMenus()

    @GetMapping("/name")
    fun getRestaurantByName(@RequestParam name: String) =
        restaurantService.findRestaurantByName(name)

    @GetMapping("/email")
    fun getRestaurantByEmail(@RequestParam email: String) =
        restaurantService.findByEmail(email)

    @GetMapping("/address")
    fun getRestaurantByAddress(@RequestParam address: String) =
        restaurantService.findByAddress(address)

    @GetMapping("/phone")
    fun getRestaurantByPhone(@RequestParam phone: String) =
        restaurantService.findByPhone(phone)

    @PostMapping
    fun addRestaurant(@RequestBody request: RestaurantRequestDTO): ResponseEntity<HttpStatus> =
        restaurantService.addRestaurant(request)

    @GetMapping("/byIds")
    fun getRestaurantsByIds(@RequestParam("ids") ids: List<Int>): ResponseEntity<List<RestaurantResponseDTO>> {
        return restaurantService.findRestaurantsByIds(ids)
    }

}