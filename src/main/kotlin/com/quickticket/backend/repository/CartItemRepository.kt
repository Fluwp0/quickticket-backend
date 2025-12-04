package com.quickticket.backend.repository

import com.quickticket.backend.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {

    // Spring entiende "user.email" con este nombre
    fun findByUserEmail(email: String): List<CartItem>
}
