package com.subproblem.restaurantservice.entity

import jakarta.persistence.*


@Entity
@Table(name = "inventory")
class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    lateinit var restaurant: Restaurant

    lateinit var itemName: String

    var quantity: Int = 0

}