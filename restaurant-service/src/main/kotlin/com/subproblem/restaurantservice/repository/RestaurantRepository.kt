package com.subproblem.restaurantservice.repository

import com.subproblem.restaurantservice.entity.Restaurant
import kotlinx.coroutines.flow.Flow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RestaurantRepository : JpaRepository<Restaurant,Int> {

    fun findByName(name: String): Restaurant?

    fun findByAddressContaining(keyword: String): List<Restaurant>

    fun findByPhone(phone: String): Restaurant?

    fun findByEmail(email: String): Restaurant?

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menus")
    fun findAllWithMenus(): List<Restaurant>

}