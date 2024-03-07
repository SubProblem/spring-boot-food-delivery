package com.subproblem.securityservice.service

import com.subproblem.securityservice.dto.TokenValidationDTO
import com.subproblem.securityservice.jwt.JwtService
import com.subproblem.securityservice.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class TokenValidationService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository
) {

    fun validateJwtToken(token: String): ResponseEntity<TokenValidationDTO> {

        val email = jwtService
            .extractEmailFromToken(token)
            .orEmpty()

        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found")

        jwtService.isTokenValid(token, email)
            .throwIfFalse("Token is not correct")

        val response = TokenValidationDTO(
            user.id,
            user.email
        )
        return ResponseEntity.ok(response)
    }

    private fun Boolean.throwIfFalse(exceptionMessage: String) {
        if (!this) {
            throw RuntimeException(exceptionMessage)
        }
    }
}