package com.subproblem.restaurantservice.controller

import com.subproblem.restaurantservice.dto.request.MenuRequestDTO
import com.subproblem.restaurantservice.dto.response.MenuResponseDTO
import com.subproblem.restaurantservice.service.MenuService
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
@RequestMapping("/api/v1/menus")
class MenuController(private val menuService: MenuService) {

    @GetMapping
    fun getAllMenus(): ResponseEntity<List<MenuResponseDTO>> =
        menuService.getAllMenus()

    @GetMapping("/items")
    fun getAllMenusWithItems(): ResponseEntity<List<MenuResponseDTO>> =
        menuService.getAllMenusWithItems()

    @GetMapping("/restaurant")
    fun getMenusByRestaurant(@RequestParam id: Int) =
        menuService.getMenusByRestaurant(id)

    @PostMapping
    fun addMenu(@RequestBody request: MenuRequestDTO): ResponseEntity<HttpStatus> =
        menuService.addMenu(request)
}