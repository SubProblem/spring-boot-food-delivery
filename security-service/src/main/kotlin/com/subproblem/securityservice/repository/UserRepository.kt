package com.subproblem.securityservice.repository

import com.subproblem.securityservice.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Int> {

    fun findByEmail(email: String?): User?
    fun existsByEmail(email: String?): Boolean
}