package com.subproblem.restaurantservice.entity

import jakarta.persistence.*
import java.math.BigDecimal


@Entity
@Table(name = "menu_items")
class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    lateinit var menu: Menu

    lateinit var name: String

    var description: String? = null

    lateinit var price: BigDecimal
}