package com.subproblem.orderservice.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    var userId: Int = 0

    var restaurantId: Int = 0

    @Enumerated(EnumType.STRING)
    lateinit var status: OrderStatus

    lateinit var totalAmount: BigDecimal

    var createdAt: LocalDateTime = LocalDateTime.now()

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var orderItems: MutableList<OrderItem> = mutableListOf()

}