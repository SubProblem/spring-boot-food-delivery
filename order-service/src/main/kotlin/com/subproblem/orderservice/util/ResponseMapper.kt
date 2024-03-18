package com.subproblem.orderservice.util

import com.subproblem.orderservice.dto.response.OrderItemResponseDTO
import com.subproblem.orderservice.dto.response.OrderResponseDTO
import com.subproblem.orderservice.entity.Order
import com.subproblem.orderservice.entity.OrderItem
import org.springframework.stereotype.Service

@Service
class ResponseMapper {

    fun orderItemToResponseDTO(orderItem: OrderItem): OrderItemResponseDTO {
        return OrderItemResponseDTO(
            id = orderItem.id,
            orderId = orderItem.order.id,
            menuItemId = orderItem.menuItemId,
            quantity = orderItem.quantity,
            unitPrice = orderItem.unitPrice
        )
    }

    fun orderToResponseDTO(order: Order): OrderResponseDTO {
        return OrderResponseDTO(
            order.id,
            order.userId,
            order.restaurantId,
            order.status.name,
            order.totalAmount,
            order.createdAt,
            orderItemResponseDTOtoList(order.orderItems)
        )
    }

    fun orderItemResponseDTOtoList(orderItems: List<OrderItem>): List<OrderItemResponseDTO> {
        return orderItems.map {
            orderItemToResponseDTO(it)
        }.toList()
    }

    fun orderResponseDTOtoList(orders: List<Order>): List<OrderResponseDTO> {
        return orders.map {
            orderToResponseDTO(it)
        }.toList()
    }
}