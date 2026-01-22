package com.book.Archiving.domain.auth

import com.book.Archiving.domain.user.SocialType

interface SocialLoginClient {

    fun getProviderType(): SocialType

    fun getUserInfo(accessToken: String): SocialUserInfo

}
