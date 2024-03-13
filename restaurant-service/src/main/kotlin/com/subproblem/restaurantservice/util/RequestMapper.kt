package com.subproblem.restaurantservice.util

import com.subproblem.restaurantservice.dto.request.MenuItemRequestDTO
import com.subproblem.restaurantservice.dto.request.MenuRequestDTO
import com.subproblem.restaurantservice.dto.request.RestaurantRequestDTO
import com.subproblem.restaurantservice.entity.Menu
import com.subproblem.restaurantservice.entity.Restaurant
import org.springframework.stereotype.Service

@Service
class RequestMapper {

    fun requestToMenuItem(request: MenuItemRequestDTO) =
        MenuItemRequestDTO(
            request.menuId,
            request.name,
            request.description,
            request.price
        )

    fun requestToMenu(request: MenuRequestDTO, restaurant: Restaurant) =
        Menu(
            restaurant,
            request.name,
            request.description
        )

    fun requestToRestaurant(request: RestaurantRequestDTO) =
        Restaurant(
            request.name,
            request.description,
            request.address,
            request.phone,
            request.email
        )


}