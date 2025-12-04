package com.quickticket.backend.service

import com.quickticket.backend.dto.ProductRequest
import com.quickticket.backend.dto.ProductResponse
import com.quickticket.backend.model.Product
import com.quickticket.backend.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun findAll(): List<ProductResponse> =
        productRepository.findAll().map(ProductResponse::fromEntity)

    fun findById(id: Long): ProductResponse =
        productRepository.findById(id)
            .map(ProductResponse::fromEntity)
            .orElseThrow { IllegalArgumentException("Producto no encontrado") }

    @Transactional
    fun create(request: ProductRequest): ProductResponse {
        val entity = Product(
            name = request.name,
            category = request.category,
            price = request.price,
            stock = request.stock
        )
        return ProductResponse.fromEntity(productRepository.save(entity))
    }

    @Transactional
    fun update(id: Long, request: ProductRequest): ProductResponse {
        val product = productRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Producto no encontrado") }

        product.name = request.name
        product.category = request.category
        product.price = request.price
        product.stock = request.stock

        return ProductResponse.fromEntity(productRepository.save(product))
    }

    @Transactional
    fun delete(id: Long) {
        if (!productRepository.existsById(id)) {
            throw IllegalArgumentException("Producto no encontrado")
        }
        productRepository.deleteById(id)
    }
}
