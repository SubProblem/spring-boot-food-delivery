package com.subproblem.securityservice.dto

data class RegistrationDTO(
    val firstname: String,
    val lastname: String,
    val age: Int,
    val email: String,
    val password: String,
    val role: String
)
