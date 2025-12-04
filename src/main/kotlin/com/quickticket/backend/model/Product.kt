package com.quickticket.backend.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var category: String,      // bebida, galleta, energética, etc.

    @Column(nullable = false)
    var price: BigDecimal,

    @Column(nullable = false)
    var stock: Int = 0
)
