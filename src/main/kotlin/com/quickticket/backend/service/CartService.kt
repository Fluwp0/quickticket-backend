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

    // Obtener carrito por email
    fun getCart(email: String): List<CartItemResponse> {
        val cleanEmail = email.trim().lowercase()

        val user = userRepository.findByEmail(cleanEmail)
            ?: throw IllegalArgumentException("Usuario no encontrado")

        val items = cartItemRepository.findByUserEmail(cleanEmail)
        return items.map { CartItemResponse.fromEntity(it) }
    }

    // Agregar producto al carrito
    @Transactional
    fun addItem(request: CartItemRequest): CartItemResponse {
        val cleanEmail = request.userEmail.trim().lowercase()

        val user = userRepository.findByEmail(cleanEmail)
            ?: throw IllegalArgumentException("Usuario no encontrado")

        val product = productRepository.findById(request.productId)
            .orElseThrow { IllegalArgumentException("Producto no encontrado") }

        if (request.quantity <= 0) {
            throw IllegalArgumentException("La cantidad debe ser mayor a cero")
        }

        val existing = cartItemRepository.findByUserAndProduct(user, product)

        val entity: CartItem = if (existing != null) {
            existing.quantity += request.quantity
            existing
        } else {
            CartItem(
                user = user,
                product = product,
                quantity = request.quantity
            )
        }

        return CartItemResponse.fromEntity(cartItemRepository.save(entity))
    }

    // Actualizar cantidad
    @Transactional
    fun updateItem(id: Long, request: CartItemUpdateRequest): CartItemResponse {
        if (request.quantity <= 0) {
            throw IllegalArgumentException("La cantidad debe ser mayor a cero")
        }

        val entity = cartItemRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Item no encontrado en el carrito") }

        entity.quantity = request.quantity
        return CartItemResponse.fromEntity(cartItemRepository.save(entity))
    }

    // Eliminar 1 ítem
    @Transactional
    fun deleteItem(id: Long) {
        if (!cartItemRepository.existsById(id)) {
            throw IllegalArgumentException("Item no encontrado en el carrito")
        }
        cartItemRepository.deleteById(id)
    }

    // Vaciar carrito de un usuario
    @Transactional
    fun clearCart(email: String) {
        val cleanEmail = email.trim().lowercase()
        val items = cartItemRepository.findByUserEmail(cleanEmail)
        cartItemRepository.deleteAll(items)
    }
}
