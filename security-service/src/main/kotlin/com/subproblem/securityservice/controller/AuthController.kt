package com.subproblem.securityservice.controller

import com.subproblem.securityservice.dto.AuthenticationDTO
import com.subproblem.securityservice.dto.RegistrationDTO
import com.subproblem.securityservice.dto.TokenValidationDTO
import com.subproblem.securityservice.entity.User
import com.subproblem.securityservice.service.AuthenticationService
import com.subproblem.securityservice.service.RegistrationService
import com.subproblem.securityservice.service.TokenValidationService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val registrationService: RegistrationService,
    private val tokenValidationService: TokenValidationService
) {


    @PostMapping("/register")
    fun register(@RequestBody request: RegistrationDTO): ResponseEntity<Any> =
        registrationService.registerUser(request)

    @PostMapping("/login")
    fun authenticate(@RequestBody request: AuthenticationDTO): ResponseEntity<Any> =
        authenticationService.authenticateUser(request)

    @PostMapping("/validate")
    fun validateToken(@RequestParam token: String): ResponseEntity<TokenValidationDTO> =
        tokenValidationService.validateJwtToken(token)

}