package com.subproblem.restaurantservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "menus")
class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    lateinit var restaurant: Restaurant

    lateinit var name: String

    var description: String? = null

    @OneToMany(mappedBy = "menu", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var menuItems: MutableList<MenuItem> = mutableListOf()

    constructor(restaurant: Restaurant, name: String, description: String?) {
        this.restaurant = restaurant
        this.name = name
        this.description = description
    }
}