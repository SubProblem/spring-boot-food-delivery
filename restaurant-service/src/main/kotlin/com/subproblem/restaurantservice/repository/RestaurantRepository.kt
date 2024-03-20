package com.subproblem.restaurantservice.repository

import com.subproblem.restaurantservice.dto.RestaurantProjection
import com.subproblem.restaurantservice.entity.Restaurant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RestaurantRepository : JpaRepository<Restaurant,Int> {

    fun findByName(name: String): Restaurant?

    fun findByAddressContaining(keyword: String): List<Restaurant>

    fun findByPhone(phone: String): Restaurant?

    fun findByEmail(email: String): Restaurant?

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menus")
    fun findAllWithMenus(): List<Restaurant>

    @Query("""
        SELECT NEW com.subproblem.restaurantservice.dto.RestaurantProjection(r.id, r.name, r.description, r.address, r.phone, r.email)
        FROM Restaurant r
        WHERE r.id IN :ids
    """)
    fun findByIds(ids: List<Int>): List<RestaurantProjection>
}