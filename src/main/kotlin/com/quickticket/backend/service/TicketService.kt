package com.quickticket.backend.service

import com.quickticket.backend.dto.TicketStatusResponse
import com.quickticket.backend.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId

@Service
class TicketService(
    private val userRepository: UserRepository
) {

    private fun todayKey(): String {
        // Ajusta la zona si quieres otra
        return LocalDate.now(ZoneId.of("America/Santiago")).toString() // "yyyy-MM-dd"
    }

    fun getStatus(email: String): TicketStatusResponse {
        val normalizedEmail = email.trim().lowercase()
        val user = userRepository.findByEmail(normalizedEmail)
            ?: throw IllegalArgumentException("Usuario no encontrado")

        val today = todayKey()
        val usedToday = user.lastTicketDate == today

        return TicketStatusResponse(
            ticketUsedToday = usedToday,
            lastTicketDate = user.lastTicketDate
        )
    }

    @Transactional
    fun claimTicket(email: String): TicketStatusResponse {
        val normalizedEmail = email.trim().lowercase()
        val user = userRepository.findByEmail(normalizedEmail)
            ?: throw IllegalArgumentException("Usuario no encontrado")

        val today = todayKey()

        if (user.lastTicketDate == today) {
            throw IllegalStateException("Ya reclamaste tu ticket hoy")
        }

        user.lastTicketDate = today
        userRepository.save(user)

        return TicketStatusResponse(
            ticketUsedToday = true,
            lastTicketDate = today
        )
    }
}
