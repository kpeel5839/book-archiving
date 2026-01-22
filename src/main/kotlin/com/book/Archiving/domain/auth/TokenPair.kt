package com.book.Archiving.domain.auth

data class TokenPair(

    val accessToken: String,

    val refreshToken: String

)
