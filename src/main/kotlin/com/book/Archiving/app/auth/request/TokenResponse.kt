package com.book.Archiving.app.auth.request

data class TokenResponse(

    val accessToken: String,

    val refreshToken: String

) {
}