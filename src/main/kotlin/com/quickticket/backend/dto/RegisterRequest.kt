package com.quickticket.backend.dto

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val rut: String
)
