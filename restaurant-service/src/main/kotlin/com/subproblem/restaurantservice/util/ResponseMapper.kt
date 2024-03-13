package com.subproblem.restaurantservice.util

import com.subproblem.restaurantservice.dto.response.MenuItemResponseDTO
import com.subproblem.restaurantservice.dto.response.MenuResponseDTO
import com.subproblem.restaurantservice.dto.response.RestaurantResponseDTO
import com.subproblem.restaurantservice.entity.Menu
import com.subproblem.restaurantservice.entity.MenuItem
import com.subproblem.restaurantservice.entity.Restaurant
import com.subproblem.restaurantservice.repository.MenuRepository
import org.springframework.stereotype.Service

@Service
class ResponseMapper {

    fun toMenuItemResponse(menuItem: MenuItem): MenuItemResponseDTO =
        MenuItemResponseDTO(
            menuItem.id,
            menuItem.menu.id,
            menuItem.name,
            menuItem.description,
            menuItem.price
        )

    fun toMenuResponse(menu: Menu): MenuResponseDTO =
        MenuResponseDTO(
            menu.restaurant.id,
            menu.name,
            menu.description.orEmpty(),
            menuItemResponseToList(menu.menuItems) ?: emptyList()
        )


    fun toRestaurantResponse(restaurant: Restaurant): RestaurantResponseDTO =
        RestaurantResponseDTO(
            restaurant.id,
            restaurant.name,
            restaurant.description,
            restaurant.address,
            restaurant.phone,
            restaurant.email,
            menuResponseToList(restaurant.menus.toList()) ?: emptyList()
        )

    fun menuItemResponseToList(menuItems: List<MenuItem>): List<MenuItemResponseDTO> =
        menuItems.map {
            toMenuItemResponse(it)
        }.toList()


    fun menuResponseToList(menus: List<Menu>): List<MenuResponseDTO> =
        menus.map {
            toMenuResponse(it)
        }.toList()

    fun restaurantResponseToList(restaurant: List<Restaurant>): List<RestaurantResponseDTO> =
        restaurant.map {
            toRestaurantResponse(it)
        }.toList()


}