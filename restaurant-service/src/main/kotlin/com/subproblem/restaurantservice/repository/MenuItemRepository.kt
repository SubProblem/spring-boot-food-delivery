package com.subproblem.restaurantservice.repository

import com.subproblem.restaurantservice.entity.MenuItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MenuItemRepository : JpaRepository<MenuItem, Int> {
    @Query("SELECT m FROM MenuItem m WHERE m.id IN :ids")
    fun findByIds(ids: List<Int>): List<MenuItem>
}