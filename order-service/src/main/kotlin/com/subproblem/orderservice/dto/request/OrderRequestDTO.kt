package com.subproblem.orderservice.dto.request

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderRequestDTO(
    val restaurantId: Int,
    val status: String,
    val totalAmount: BigDecimal,
    val orderItems: List<OrderItemRequestDTO>
)
