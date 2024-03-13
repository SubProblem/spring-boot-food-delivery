package com.subproblem.restaurantservice.repository

import com.subproblem.restaurantservice.entity.MenuItem
import org.springframework.data.jpa.repository.JpaRepository

interface MenuItemRepository : JpaRepository<MenuItem, Int> {
}