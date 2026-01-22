package com.book.Archiving.app.auth

data class AuthResponse(

    val userId: Long,

    val accessToken: String,

    val refreshToken: String,

    val isNewUser: Boolean

)
