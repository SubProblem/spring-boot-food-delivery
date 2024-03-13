package com.subproblem.restaurantservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "restaurants")
class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    lateinit var name: String

    var description: String? = null

    var address: String? = null

    var phone: String? = null

    lateinit var email: String

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var menus: MutableList<Menu> = mutableListOf()



    constructor(name: String, description: String?, address: String?, phone: String?, email: String) {
        this.name = name
        this.description = description
        this.address = address
        this.phone = phone
        this.email = email
    }
}