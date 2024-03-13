package com.subproblem.restaurantservice.dto.response

data class MenuResponseDTO(
    val restaurantId: Int,
    val name: String,
    val description: String,
    val menuItems: List<MenuItemResponseDTO>?
)
