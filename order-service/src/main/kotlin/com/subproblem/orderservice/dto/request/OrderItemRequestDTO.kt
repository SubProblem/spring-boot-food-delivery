package com.subproblem.orderservice.dto.request

import java.math.BigDecimal

data class OrderItemRequestDTO(
    val orderId: Int,
    val menuItemId: Int,
    val quantity: Int,
    val unitPrice: BigDecimal
)
