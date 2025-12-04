package com.quickticket.backend.controller

import com.quickticket.backend.dto.ProductRequest
import com.quickticket.backend.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = ["*"])
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun findAll() = ResponseEntity.ok(productService.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) =
        try {
            ResponseEntity.ok(productService.findById(id))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    @PostMapping
    fun create(@RequestBody request: ProductRequest) =
        try {
            ResponseEntity.ok(productService.create(request))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: ProductRequest) =
        try {
            ResponseEntity.ok(productService.update(id, request))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) =
        try {
            productService.delete(id)
            ResponseEntity.noContent().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }
}
