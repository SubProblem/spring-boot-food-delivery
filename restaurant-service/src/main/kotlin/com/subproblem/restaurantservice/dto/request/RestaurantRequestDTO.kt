package com.subproblem.restaurantservice.dto.request

data class RestaurantRequestDTO(
    val name: String,
    val description: String?,
    val address: String?,
    val phone: String?,
    val email: String
)
