package com.quickticket.backend.service

import com.quickticket.backend.dto.LoginRequest
import com.quickticket.backend.dto.RegisterRequest
import com.quickticket.backend.dto.UserResponse
import com.quickticket.backend.model.User
import com.quickticket.backend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun validarRut(rut: String): Boolean {
        val regex = Regex("^[0-9]{7,8}-[0-9kK]\$")

        if (!regex.matches(rut)) return false

        val parts = rut.split("-")
        val number = parts[0].toIntOrNull() ?: return false
        val dv = parts[1].lowercase()

        var m = 0
        var s = 1
        var t = number

        while (t > 0) {
            s = (s + t % 10 * (9 - m % 6)) % 11
            t /= 10
            m++
        }

        val dvEsperado = if (s == 0) "k" else (s + 47).toChar().lowercase()

        return dv == dvEsperado
    }

    @Transactional
    fun register(request: RegisterRequest): UserResponse {
        val email = request.email.trim().lowercase()

        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("El email ya está registrado")
        }

        val user = User(
            name = request.name.trim(),
            rut = request.rut.trim(),
            email = email,
            password = request.password
        )

        return UserResponse.fromEntity(userRepository.save(user))
    }

    fun login(request: LoginRequest): UserResponse {
        val email = request.email.trim().lowercase()
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Credenciales inválidas")

        if (user.password != request.password) {
            throw IllegalArgumentException("Credenciales inválidas")
        }

        return UserResponse.fromEntity(user)
    }
}
