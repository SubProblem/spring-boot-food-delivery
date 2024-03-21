package com.subproblem.orderservice.dto

import java.math.BigDecimal

data class MenuItem(
    val id: Int,
    val menuId: Int,
    val name: String,
    val description: String?,
    val price: BigDecimal
)
