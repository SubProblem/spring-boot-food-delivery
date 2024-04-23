package com.subproblem.orderservice.producer

import java.time.LocalDateTime

data class NotificationMessage(
    val email: String,
    val dateOfOrder: LocalDateTime
)
