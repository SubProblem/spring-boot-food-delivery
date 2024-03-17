package com.subproblem.orderservice.dto.request

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderRequestDTO(
    val userId: Int,
    val restaurantId: Int,
    val status: String,
    val totalAmount: BigDecimal,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val orderItems: List<OrderItemRequestDTO>
)
