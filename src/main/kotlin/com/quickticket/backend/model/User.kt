package com.quickticket.backend.model

import jakarta.persistence.*
import java.time.Instant
import jakarta.validation.constraints.Pattern


@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["email"])
    ]
)
data class User(

    @Column(nullable = false)
    @field:Pattern(regexp = "^[0-9]{7,8}-[0-9kK]\$", message = "Formato de RUT inválido")
    var rut: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "last_ticket_date")
    var lastTicketDate: String? = null
)
