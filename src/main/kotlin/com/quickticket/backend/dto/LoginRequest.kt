package com.quickticket.backend.dto

data class LoginRequest(
    val email: String,
    val password: String,
    val rut: String
)
