package com.bookpin.app.auth.request

data class TokenResponse(

    val accessToken: String,

    val refreshToken: String

) {
}