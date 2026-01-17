package com.book.Archiving.infrastructure.client.kakao

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "kakao-auth-client",
    url = "https://kapi.kakao.com"
)
interface KakaoAuthClient {

    @GetMapping("/v2/user/me")
    fun getUserInfo(
        @RequestHeader("Authorization") authorization: String
    ): KakaoUserResponse
}
