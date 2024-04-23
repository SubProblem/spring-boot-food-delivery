package com.subproblem.orderservice.controller

import com.subproblem.orderservice.dto.request.OrderRequestDTO
import com.subproblem.orderservice.dto.response.OrderResponseDTO
import com.subproblem.orderservice.entity.Order
import com.subproblem.orderservice.service.OrderService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(private val orderService: OrderService) {


    @GetMapping
    fun getAllOrders(request: HttpServletRequest): ResponseEntity<List<OrderResponseDTO>> {
        val userId = request.getHeader("auth-header-id").toInt()
        return orderService.getAllOrdersForUser(userId)
    }

    @GetMapping("/totalAmount")
    fun getTotalAmountSpent(request: HttpServletRequest): ResponseEntity<BigDecimal> {
        val userId = request.getHeader("auth-header-id").toInt()
        return orderService.getTotalAmountSpentByUser(userId)
    }

    @PostMapping
    fun makeOrder(@RequestBody order: OrderRequestDTO, request: HttpServletRequest): ResponseEntity<OrderResponseDTO> {
        val userId = request.getHeader("auth-header-id").toInt()
        val userEmail = request.getHeader("user-email")
        return orderService.makeOrder(userId, userEmail, order)
    }

    @GetMapping("/full")
    suspend fun getOrdersWithRestaurantsAndMenuItems(request: HttpServletRequest): ResponseEntity<Any> {
        val userId = request.getHeader("auth-header-id").toInt()
        return orderService.getOrdersWithRestaurantsAndMenuItems(userId)
    }
}