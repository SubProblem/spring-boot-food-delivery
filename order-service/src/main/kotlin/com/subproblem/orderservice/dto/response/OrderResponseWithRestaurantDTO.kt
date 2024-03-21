package com.subproblem.orderservice.dto.response

import com.subproblem.orderservice.dto.Restaurant
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponseWithRestaurantDTO(
    val id: Int,
    val userId: Int,
    val restaurant: Restaurant,
    val status: String,
    val totalAmount: BigDecimal,
    val createdAt: LocalDateTime,
    val orderItems: List<OrderItemResponseWithMenuItemDTO>
)
