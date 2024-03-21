package com.subproblem.orderservice.util

import com.subproblem.orderservice.dto.MenuItem
import com.subproblem.orderservice.dto.Restaurant
import com.subproblem.orderservice.dto.response.OrderItemResponseDTO
import com.subproblem.orderservice.dto.response.OrderItemResponseWithMenuItemDTO
import com.subproblem.orderservice.dto.response.OrderResponseDTO
import com.subproblem.orderservice.dto.response.OrderResponseWithRestaurantDTO
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


    fun toOrderItemResponseWithMenuItem(orderItem: OrderItem, menuItem: MenuItem): OrderItemResponseWithMenuItemDTO {
        return OrderItemResponseWithMenuItemDTO(
            orderItem.id,
            orderItem.order.id,
            menuItem,
            orderItem.quantity,
            orderItem.unitPrice
        )
    }

    fun toOrderItemResponseWithMenuItemList(orderItems: List<OrderItem>, menuItems: List<MenuItem>): List<OrderItemResponseWithMenuItemDTO> {
        return orderItems.mapNotNull { orderItem ->
            menuItems.find { it.id == orderItem.menuItemId }?.let { menuItem ->
                toOrderItemResponseWithMenuItem(orderItem, menuItem)
            }
        }
    }

    fun toOrderResponseWithRestaurants(order: Order, restaurant: Restaurant, orderItems: List<OrderItem>, menuItems: List<MenuItem>): OrderResponseWithRestaurantDTO {
        return OrderResponseWithRestaurantDTO(
            order.id,
            order.userId,
            restaurant,
            order.status.name,
            order.totalAmount,
            order.createdAt,
            toOrderItemResponseWithMenuItemList(orderItems, menuItems)
        )
    }

    fun toOrderResponseWithRestaurantsList(orders: List<Order>, restaurants: List<Restaurant>, allOrderItems: List<OrderItem>, allMenuItems: List<MenuItem>): List<OrderResponseWithRestaurantDTO> {
        return orders.map { order ->
            val restaurant = restaurants.find { it.id == order.restaurantId }
            val items = allOrderItems.filter { it.order.id == order.id }
            val menuItemList = allMenuItems.filter { it.menuId == order.restaurantId }
            OrderResponseWithRestaurantDTO(
                id = order.id,
                userId = order.userId,
                restaurant = restaurant ?: Restaurant(0, "", "", "", "", ""), // Provide default Restaurant if not found
                status = order.status.name,
                totalAmount = order.totalAmount,
                createdAt = order.createdAt,
                orderItems = toOrderItemResponseWithMenuItemList(items, menuItemList)
            )
        }
    }

}