package com.quickticket.backend.service

import com.quickticket.backend.dto.CartItemRequest
import com.quickticket.backend.dto.CartItemResponse
import com.quickticket.backend.dto.CartItemUpdateRequest
import com.quickticket.backend.model.CartItem
import com.quickticket.backend.repository.CartItemRepository
import com.quickticket.backend.repository.ProductRepository
import com.quickticket.backend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartService(
    private val cartItemRepository: CartItemRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    fun getCartForUser(email: String): List<CartItemResponse> =
        cartItemRepository.findByUserEmail(email)
            .map(CartItemResponse::fromEntity)

    @Transactional
    fun addItem(request: CartItemRequest): CartItemResponse {
        val user = userRepository.findByEmail(request.userEmail)
            ?: throw IllegalArgumentException("Usuario no encontrado")

        val product = productRepository.findById(request.productId)
            .orElseThrow { IllegalArgumentException("Producto no encontrado") }

        if (request.quantity <= 0) {
            throw IllegalArgumentException("La cantidad debe ser mayor a cero")
        }

        val entity = CartItem(
            user = user,
            product = product,
            quantity = request.quantity
        )

        return CartItemResponse.fromEntity(cartItemRepository.save(entity))
    }

    @Transactional
    fun updateItem(id: Long, request: CartItemUpdateRequest): CartItemResponse {
        val item = cartItemRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Item no encontrado") }

        if (request.quantity <= 0) {
            throw IllegalArgumentException("La cantidad debe ser mayor a cero")
        }

        item.quantity = request.quantity
        return CartItemResponse.fromEntity(cartItemRepository.save(item))
    }

    @Transactional
    fun removeItem(id: Long) {
        if (!cartItemRepository.existsById(id)) {
            throw IllegalArgumentException("Item no encontrado")
        }
        cartItemRepository.deleteById(id)
    }

    @Transactional
    fun clearCartForUser(email: String) {
        val items = cartItemRepository.findByUserEmail(email)
        cartItemRepository.deleteAll(items)
    }
}
