package com.subproblem.orderservice.dto

data class Restaurant(
    val id: Int,
    val name: String,
    val description: String?,
    val address: String?,
    val phone: String?,
    val email: String,
)
