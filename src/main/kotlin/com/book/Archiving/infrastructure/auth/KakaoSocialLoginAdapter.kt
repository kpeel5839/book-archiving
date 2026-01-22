package com.book.Archiving.infrastructure.auth

import com.book.Archiving.domain.auth.SocialLoginClient
import com.book.Archiving.domain.auth.SocialUserInfo
import com.book.Archiving.domain.user.SocialType
import com.book.Archiving.infrastructure.auth.feign.kakao.KakaoAuthFeignClient
import org.springframework.stereotype.Component

@Component
class KakaoSocialLoginAdapter(
    private val kakaoAuthFeignClient: KakaoAuthFeignClient
) : SocialLoginClient {

    override fun getProviderType(): SocialType = SocialType.KAKAO

    override fun getUserInfo(accessToken: String): SocialUserInfo {
        val response = kakaoAuthFeignClient.getUserInfo("Bearer $accessToken")

        return SocialUserInfo(
            socialId = response.id.toString(),
            email = response.kakaoAccount?.email,
            nickname = response.kakaoAccount?.profile?.nickname,
            profileImageUrl = response.kakaoAccount?.profile?.profileImageUrl
        )
    }
}
