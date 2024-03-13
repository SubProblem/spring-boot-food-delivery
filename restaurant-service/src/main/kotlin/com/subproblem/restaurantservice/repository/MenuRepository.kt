package com.subproblem.restaurantservice.repository

import com.subproblem.restaurantservice.entity.Menu
import com.subproblem.restaurantservice.entity.Restaurant
import kotlinx.coroutines.flow.Flow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MenuRepository : JpaRepository<Menu, Int> {

    @Query("SELECT m FROM Menu m WHERE m.restaurant = :restaurant")
    fun findAllByRestaurant(restaurant: Restaurant): List<Menu>

    @Query("SELECT DISTINCT m FROM Menu m LEFT JOIN FETCH m.menuItems")
    fun findAllWithMenuItems(): List<Menu>

    @Query("SELECT DISTINCT m FROM Menu m LEFT JOIN FETCH m.menuItems")
    fun findAllWithMenuItemsAsync(): List<Menu>

}