package com.book.Archiving.infrastructure.client.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserResponse(
    val id: Long,

    @JsonProperty("connected_at")
    val connectedAt: String?,

    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount?
)

data class KakaoAccount(
    val email: String?,

    @JsonProperty("email_needs_agreement")
    val emailNeedsAgreement: Boolean?,

    @JsonProperty("is_email_valid")
    val isEmailValid: Boolean?,

    @JsonProperty("is_email_verified")
    val isEmailVerified: Boolean?,

    val profile: KakaoProfile?,

    @JsonProperty("profile_needs_agreement")
    val profileNeedsAgreement: Boolean?
)

data class KakaoProfile(
    val nickname: String?,

    @JsonProperty("thumbnail_image_url")
    val thumbnailImageUrl: String?,

    @JsonProperty("profile_image_url")
    val profileImageUrl: String?,

    @JsonProperty("is_default_image")
    val isDefaultImage: Boolean?
)
