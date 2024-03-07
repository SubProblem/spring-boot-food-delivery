package com.subproblem.securityservice.service

import com.subproblem.securityservice.config.CustomUserDetailsService
import com.subproblem.securityservice.dto.AuthenticationDTO
import com.subproblem.securityservice.jwt.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    fun authenticateUser(request: AuthenticationDTO): ResponseEntity<Any> {
        val email = request.email
        val password = request.password

        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
            email,
            password
        )

        authenticationManager.authenticate(usernamePasswordAuthenticationToken)

        val token = jwtService.generateJwtToken(email)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(token)
    }
}