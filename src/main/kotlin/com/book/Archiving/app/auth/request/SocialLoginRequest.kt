package com.book.Archiving.app.auth.request

import com.book.Archiving.domain.user.SocialType

data class SocialLoginRequest(

    val socialType: SocialType,

    val accessToken: String

) {
}