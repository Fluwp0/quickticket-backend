package com.quickticket.backend.dto

import com.quickticket.backend.model.Product
import java.math.BigDecimal

data class ProductRequest(
    val name: String,
    val category: String,
    val price: BigDecimal,
    val stock: Int
)

data class ProductResponse(
    val id: Long,
    val name: String,
    val category: String,
    val price: BigDecimal,
    val stock: Int
) {
    companion object {
        fun fromEntity(product: Product) = ProductResponse(
            id = product.id,
            name = product.name,
            category = product.category,
            price = product.price,
            stock = product.stock
        )
    }
}
