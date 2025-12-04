package com.quickticket.backend.dto

import com.quickticket.backend.model.CartItem
import java.math.BigDecimal

data class CartItemRequest(
    val userEmail: String,
    val productId: Long,
    val quantity: Int
)

data class CartItemUpdateRequest(
    val quantity: Int
)

data class CartItemResponse(
    val id: Long,
    val userEmail: String,
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val totalPrice: BigDecimal
) {
    companion object {
        fun fromEntity(entity: CartItem) = CartItemResponse(
            id = entity.id,
            userEmail = entity.user.email,
            productId = entity.product.id,
            productName = entity.product.name,
            quantity = entity.quantity,
            unitPrice = entity.product.price,
            totalPrice = entity.product.price.multiply(BigDecimal(entity.quantity))
        )
    }
}
