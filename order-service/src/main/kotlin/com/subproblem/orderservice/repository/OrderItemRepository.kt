package com.subproblem.orderservice.repository;

import com.subproblem.orderservice.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface OrderItemRepository : JpaRepository<OrderItem, Int> {

    fun findByOrderId(orderId: Int): List<OrderItem>
    fun findByMenuItemId(menuItemId: Int): List<OrderItem>
    fun findByOrderUserId(userId: Int): List<OrderItem>
    fun findByOrderRestaurantId(restaurantId: Int): List<OrderItem>

    @Query("""
        SELECT i FROM OrderItem
        JOIN i.order o
        WHERE o.userId = :userId
        AND
        o.createdAt BETWEEN :startDateTime AND :endDateTime
    """)
    fun findUserOrderCreatedBetween(
        userId: Int,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime
    ): List<OrderItem>
}