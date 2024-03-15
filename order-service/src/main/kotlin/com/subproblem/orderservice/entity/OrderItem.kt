package com.subproblem.orderservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity
class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    var orderId: Int = 0

    var menuItemId: Int = 0

    var quantity: Int = 0

    lateinit var unitPrice: BigDecimal
}