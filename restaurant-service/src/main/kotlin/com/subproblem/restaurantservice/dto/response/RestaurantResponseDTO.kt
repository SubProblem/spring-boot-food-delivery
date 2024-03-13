package com.subproblem.restaurantservice.dto.response

data class RestaurantResponseDTO(
    val id: Int,
    val name: String,
    val description: String?,
    val address: String?,
    val phone: String?,
    val email: String,
    val menus: List<MenuResponseDTO>?
)
