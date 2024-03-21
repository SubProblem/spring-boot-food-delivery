package com.subproblem.restaurantservice.dto

data class RestaurantProjection(
    val id: Int,
    val name: String,
    val description: String?,
    val address: String?,
    val phone: String?,
    val email: String
)
