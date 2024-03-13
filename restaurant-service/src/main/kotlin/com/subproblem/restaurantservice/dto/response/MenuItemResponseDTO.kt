package com.subproblem.restaurantservice.dto.response

import java.math.BigDecimal

data class MenuItemResponseDTO(
    val id: Int,
    val menuId: Int,
    val name: String,
    val description: String?,
    val price: BigDecimal
)
