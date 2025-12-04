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

    @GetMapping("/{email}")
    fun getCart(@PathVariable email: String): ResponseEntity<Any> =
        try {
            ResponseEntity.ok(cartService.getCart(email))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    @PostMapping
    fun addItem(@RequestBody request: CartItemRequest): ResponseEntity<Any> =
        try {
            ResponseEntity.ok(cartService.addItem(request))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    @PutMapping("/{id}")
    fun updateItem(
        @PathVariable id: Long,
        @RequestBody request: CartItemUpdateRequest
    ): ResponseEntity<Any> =
        try {
            ResponseEntity.ok(cartService.updateItem(id, request))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Long): ResponseEntity<Any> =
        try {
            cartService.deleteItem(id)
            ResponseEntity.noContent().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    @DeleteMapping("/clear/{email}")
    fun clearCart(@PathVariable email: String): ResponseEntity<Any> =
        try {
            cartService.clearCart(email)
            ResponseEntity.noContent().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }
}
