package com.subproblem.orderservice.dto.response

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponseDTO(
    val id: Int,
    val userId: Int,
    val restaurantId: Int,
    val status: String,
    val totalAmount: BigDecimal,
    val createdAt: LocalDateTime,
    val orderItems: List<OrderItemResponseDTO>
)
