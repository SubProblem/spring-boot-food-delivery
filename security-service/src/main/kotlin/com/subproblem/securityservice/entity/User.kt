package com.subproblem.securityservice.entity

import jakarta.persistence.*


@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    var firstname: String = ""

    var lastname: String = ""

    @Column(unique = true)
    var email: String = ""

    var password: String = ""

    @Enumerated(EnumType.STRING)
    var role: Role? = null

    constructor(firstname: String, lastname: String, email: String, password: String, role: Role) {
        this.firstname = firstname
        this.lastname = lastname
        this.email = email
        this.password = password
        this.role = role
    }
}