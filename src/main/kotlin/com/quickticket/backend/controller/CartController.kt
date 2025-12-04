package com.quickticket.backend.controller

import com.quickticket.backend.dto.CartItemRequest
import com.quickticket.backend.dto.CartItemUpdateRequest
import com.quickticket.backend.service.CartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = ["*"])
class CartController(
    private val cartService: CartService
) {

    // Obtener carrito del usuario (READ)
    @GetMapping
    fun getCart(@RequestParam email: String) =
        ResponseEntity.ok(cartService.getCartForUser(email))

    // Agregar producto al carrito (CREATE)
    @PostMapping
    fun addItem(@RequestBody request: CartItemRequest) =
        try {
            ResponseEntity.ok(cartService.addItem(request))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    // Actualizar cantidad (UPDATE)
    @PutMapping("/{id}")
    fun updateItem(
        @PathVariable id: Long,
        @RequestBody request: CartItemUpdateRequest
    ) =
        try {
            ResponseEntity.ok(cartService.updateItem(id, request))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    // Eliminar item (DELETE)
    @DeleteMapping("/{id}")
    fun removeItem(@PathVariable id: Long) =
        try {
            cartService.removeItem(id)
            ResponseEntity.noContent().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    // Vaciar carrito de un usuario
    @DeleteMapping("/clear")
    fun clearCart(@RequestParam email: String) =
        try {
            cartService.clearCartForUser(email)
            ResponseEntity.noContent().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }
}
