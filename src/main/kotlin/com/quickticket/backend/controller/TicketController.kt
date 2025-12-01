package com.quickticket.backend.controller

import com.quickticket.backend.dto.TicketClaimRequest
import com.quickticket.backend.service.TicketService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = ["*"])
class TicketController(
    private val ticketService: TicketService
) {

    @GetMapping("/status")
    fun getStatus(@RequestParam email: String): ResponseEntity<Any> =
        try {
            ResponseEntity.ok(ticketService.getStatus(email))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }

    @PostMapping("/claim")
    fun claim(@RequestBody request: TicketClaimRequest): ResponseEntity<Any> =
        try {
            ResponseEntity.ok(ticketService.claimTicket(request.email))
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to (e.message ?: "Error")))
        }
}
