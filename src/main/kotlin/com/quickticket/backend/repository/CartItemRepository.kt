package com.quickticket.backend.repository

import com.quickticket.backend.model.CartItem
import com.quickticket.backend.model.Product
import com.quickticket.backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {

    fun findByUserEmail(email: String): List<CartItem>

    fun findByUserAndProduct(user: User, product: Product): CartItem?
}
