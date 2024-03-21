package com.subproblem.orderservice.dto.response

import com.subproblem.orderservice.dto.MenuItem
import java.math.BigDecimal

data class OrderItemResponseWithMenuItemDTO(
    val id: Int,
    val orderId: Int,
    val menuItem: MenuItem,
    val quantity: Int,
    val unitPrice: BigDecimal
)
