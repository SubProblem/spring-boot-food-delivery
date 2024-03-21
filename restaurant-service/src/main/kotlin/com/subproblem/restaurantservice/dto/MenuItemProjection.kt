package com.subproblem.restaurantservice.dto

import java.math.BigDecimal

data class MenuItemProjection(
    val id: Int,
    val menuId: Int,
    val name: String,
    val description: String?,
    val price: BigDecimal
)
