package com.bookpin.infrastructure.auth

data class AppleIdTokenPayload(
    val sub: String,
    val email: String?
)
