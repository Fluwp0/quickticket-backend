package com.quickticket.backend.controller

import com.quickticket.backend.dto.LoginRequest
import com.quickticket.backend.dto.RegisterRequest
import com.quickticket.backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


private fun validarRut(rut: String): Boolean {

    val regex = Regex("^[0-9]{7,8}-[0-9kK]\$")
    if (!regex.matches(rut)) return false

    val (numeroStr, dvIngresadoRaw) = rut.split("-")
    val dvIngresado = dvIngresadoRaw.lowercase()

    var factor = 2
    var suma = 0

    for (i in numeroStr.length - 1 downTo 0) {
        val digito = numeroStr[i] - '0'
        suma += digito * factor
        factor++
        if (factor > 7) factor = 2
    }

    val resto = suma % 11
    val dvCalculado = when (val resultado = 11 - resto) {
        11 -> "0"
        10 -> "k"
        else -> resultado.toString()
    }

    return dvIngresado == dvCalculado
}

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["*"])
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<Any> {
        return try {
            // Validar RUT antes de registrar
            if (!validarRut(request.rut)) {
                ResponseEntity
                    .badRequest()
                    .body(mapOf("error" to "RUT inválido. Formato esperado: 11111111-1"))
            } else {
                ResponseEntity.ok(userService.register(request))
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        return try {
            // Validar RUT antes de intentar login
            if (!validarRut(request.rut)) {
                ResponseEntity
                    .badRequest()
                    .body(mapOf("error" to "RUT inválido. Formato esperado: 11111111-1"))
            } else {
                ResponseEntity.ok(userService.login(request))
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }
}
