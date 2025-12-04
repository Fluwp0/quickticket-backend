package com.quickticket.backend.controller

import com.quickticket.backend.dto.UserResponse
import com.quickticket.backend.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["*"])
class UserController(
    private val userRepository: UserRepository
) {

    @GetMapping
    fun findAll(): List<UserResponse> =
        userRepository.findAll().map(UserResponse::fromEntity)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Any> {
        val optionalUser = userRepository.findById(id)

        return if (optionalUser.isPresent) {
            val user = optionalUser.get()
            val dto = UserResponse.fromEntity(user)
            ResponseEntity.ok(dto)          // ResponseEntity<Any>
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> =
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
}
