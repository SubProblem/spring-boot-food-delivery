package com.subproblem.securityservice.service

import com.subproblem.securityservice.dto.RegistrationDTO
import com.subproblem.securityservice.entity.Role
import com.subproblem.securityservice.entity.User
import com.subproblem.securityservice.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegistrationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {


    fun registerUser(request: RegistrationDTO): ResponseEntity<Any> {
        if (userRepository.existsByEmail(request.email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Email is already used")
        }

        val user = User(
            request.firstname,
            request.lastname,
            request.email,
            passwordEncoder.encode(request.password),
            role = Role.valueOf(request.role.uppercase())
        )

        userRepository.save(user)

        return ResponseEntity.status(HttpStatus.CREATED)
            .body("User Created")
    }
}