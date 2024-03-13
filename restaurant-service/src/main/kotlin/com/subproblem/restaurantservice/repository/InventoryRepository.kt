package com.subproblem.restaurantservice.repository

import com.subproblem.restaurantservice.entity.Inventory
import org.springframework.data.jpa.repository.JpaRepository

interface InventoryRepository : JpaRepository<Inventory, Int> {
}