package com.book.Archiving.infrastructure.auth

data class AppleIdTokenPayload(
    val sub: String,
    val email: String?
)
