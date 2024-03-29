package com.subproblem.orderservice.repository;

import com.subproblem.orderservice.entity.Order
import com.subproblem.orderservice.entity.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.math.BigDecimal

interface OrderRepository : JpaRepository<Order, Int> {
    @Query("""
        SELECT o FROM Order o
        JOIN FETCH o.orderItems
        WHERE o.userId = :userId
    """)
    fun findByUserId(userId: Int): List<Order>
    fun findByRestaurantIdAndUserId(restaurantId: Int, userId: Int): List<Order>
    fun findByStatus(status: OrderStatus): List<Order>
    fun findByUserIdAndStatus(userId: Int, status: OrderStatus): List<Order>

    @Query("""
        SELECT o FROM Order o
        JOIN FETCH o.orderItems
        WHERE o.id = :orderId AND o.userId = :userId
    """)
    fun findOrderWithOrderItemsByIdAndUserId(orderId: Int, userId: Int): Order?

    @Query("""
        SELECT SUM(o.totalAmount) FROM Order o
        Where o.userId = :userId
    """)
    fun getTotalAmountSpentByUser(userId: Int): BigDecimal?

}