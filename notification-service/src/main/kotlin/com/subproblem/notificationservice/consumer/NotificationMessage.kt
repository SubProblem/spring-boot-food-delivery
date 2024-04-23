package com.subproblem.notificationservice.consumer

import java.time.LocalDateTime

data class NotificationMessage(
    val email: String,
    val dateOfOrder: LocalDateTime
)
