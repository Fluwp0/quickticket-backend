package com.quickticket.backend.config

import com.quickticket.backend.model.Product
import com.quickticket.backend.repository.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DataInitializer(
    private val productRepository: ProductRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // Si ya hay productos, no hacemos nada (para no duplicar)
        if (productRepository.count() > 0) return

        val products = listOf(
            Product(
                name = "Coca Cola lata 350ml",
                category = "bebida",
                price = BigDecimal("1000"),
                stock = 50
            ),
            Product(
                name = "Pepsi lata 350ml",
                category = "bebida",
                price = BigDecimal("900"),
                stock = 40
            ),
            Product(
                name = "Galletas Oreo 6 unidades",
                category = "galleta",
                price = BigDecimal("800"),
                stock = 30
            ),
            Product(
                name = "Barra de cereal",
                category = "snack",
                price = BigDecimal("700"),
                stock = 25
            ),
            Product(
                name = "Bebida energética 473ml",
                category = "energetica",
                price = BigDecimal("1500"),
                stock = 20
            )
        )

        productRepository.saveAll(products)
    }
}
