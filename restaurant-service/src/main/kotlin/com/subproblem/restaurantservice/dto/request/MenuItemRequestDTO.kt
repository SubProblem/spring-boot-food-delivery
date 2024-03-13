package com.subproblem.restaurantservice.dto.request

import java.math.BigDecimal

data class MenuItemRequestDTO(
    val menuId: Int,
    val name: String,
    val description: String,
    val price: BigDecimal
)
