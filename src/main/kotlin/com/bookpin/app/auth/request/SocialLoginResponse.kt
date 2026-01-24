package com.bookpin.app.auth.request

data class SocialLoginResponse(

    val userId: Long,

    val accessToken: String,

    val refreshToken: String,

    val isNewUser: Boolean

) {

}