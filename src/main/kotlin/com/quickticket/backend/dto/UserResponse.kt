package com.quickticket.backend.dto

import com.quickticket.backend.model.User
import java.time.Instant

data class UserResponse(
    val id: Long,
    val name: String,
    val rut: String,
    val email: String,
    val createdAt: Instant
) {
    companion object {
        fun fromEntity(user: User) = UserResponse(
            id = user.id,
            name = user.name,
            rut = user.rut,
            email = user.email,
            createdAt = user.createdAt
        )
    }
}
