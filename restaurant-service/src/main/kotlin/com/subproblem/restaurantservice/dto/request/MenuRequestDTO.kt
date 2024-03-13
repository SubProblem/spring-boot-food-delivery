package com.subproblem.restaurantservice.dto.request

data class MenuRequestDTO(
    val restaurantId: Int,
    val name: String,
    val description: String?
)
