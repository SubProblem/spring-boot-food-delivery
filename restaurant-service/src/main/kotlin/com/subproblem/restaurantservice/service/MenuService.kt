package com.subproblem.restaurantservice.service

import com.subproblem.restaurantservice.dto.MenuItemProjection
import com.subproblem.restaurantservice.dto.request.MenuItemRequestDTO
import com.subproblem.restaurantservice.dto.request.MenuRequestDTO
import com.subproblem.restaurantservice.dto.response.MenuItemResponseDTO
import com.subproblem.restaurantservice.dto.response.MenuResponseDTO
import com.subproblem.restaurantservice.dto.response.RestaurantResponseDTO
import com.subproblem.restaurantservice.entity.MenuItem
import com.subproblem.restaurantservice.repository.MenuItemRepository
import com.subproblem.restaurantservice.repository.MenuRepository
import com.subproblem.restaurantservice.repository.RestaurantRepository
import com.subproblem.restaurantservice.util.RequestMapper
import com.subproblem.restaurantservice.util.ResponseMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MenuService(
    private val restaurantRepository: RestaurantRepository,
    private val menuRepository: MenuRepository,
    private val requestMapper: RequestMapper,
    private val responseMapper: ResponseMapper,
    private val menuItemRepository: MenuItemRepository
) {

    fun getAllMenus(): ResponseEntity<List<MenuResponseDTO>> {
        val menus = menuRepository.findAll()
        val menuResponseToList = responseMapper.menuResponseToList(menus)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(menuResponseToList)
    }

    fun getAllMenusWithItems(): ResponseEntity<List<MenuResponseDTO>> {
        val menus = menuRepository.findAllWithMenuItems()
        val menuResponseToList = responseMapper.menuResponseToList(menus)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(menuResponseToList)
    }

    fun getMenusByRestaurant(restaurantId: Int): ResponseEntity<List<MenuResponseDTO>> {

        val restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow { NoSuchElementException("restaurant not found") }

        val menus = menuRepository.findAllByRestaurant(restaurant)

        val response = responseMapper.menuResponseToList(menus)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }


    fun addMenu(request: MenuRequestDTO): ResponseEntity<HttpStatus> {

        val restaurant = restaurantRepository.findById(request.restaurantId)
            .orElseThrow { EntityNotFoundException("Restaurant not found") }

        val menu = requestMapper.requestToMenu(request, restaurant)

        menuRepository.save(menu)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @Transactional
    fun getMenuItemsByIds(ids: List<Int>): ResponseEntity<List<MenuItemProjection>> {
        val menuItems = menuItemRepository.findByIds(ids)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(menuItems)
    }

    fun addMenuItems(request: MenuItemRequestDTO): ResponseEntity<HttpStatus> {

        val menu = menuRepository.findById(request.menuId)
            .orElseThrow { NoSuchElementException("Menu not found") }

        val menuItem = MenuItem().apply {
            this.menu = menu
            name = request.name
            description = request.description
            price = request.price
        }

        menuItemRepository.save(menuItem)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }
}