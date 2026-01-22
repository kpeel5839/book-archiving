package com.book.Archiving.domain.auth

data class SocialUserInfo(

    val socialId: String,

    val email: String?,

    val nickname: String?,

    val profileImageUrl: String?

)
