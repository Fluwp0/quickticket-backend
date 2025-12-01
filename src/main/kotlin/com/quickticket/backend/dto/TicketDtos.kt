package com.quickticket.backend.dto

data class TicketStatusResponse(
    val ticketUsedToday: Boolean,
    val lastTicketDate: String?
)

data class TicketClaimRequest(
    val email: String
)
