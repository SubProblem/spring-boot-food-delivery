package com.subproblem.orderservice.dto.response

import java.math.BigDecimal

data class OrderItemResponseDTO(
    val id: Int,
    val orderId: Int,
    val menuItemId: Int,
    val quantity: Int,
    val unitPrice: BigDecimal
)