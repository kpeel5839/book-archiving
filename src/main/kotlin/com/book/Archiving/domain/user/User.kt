package com.book.Archiving.domain.user

import java.time.LocalDateTime

data class User(
    val id: Long = 0,
    val socialId: String,
    val socialProvider: SocialType,
    val email: String? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun create(
            socialId: String,
            socialProvider: SocialType,
            email: String? = null,
            nickname: String? = null,
            profileImageUrl: String? = null
        ): User {
            return User(
                socialId = socialId,
                socialProvider = socialProvider,
                email = email,
                nickname = nickname,
                profileImageUrl = profileImageUrl
            )
        }
    }

    fun updateProfile(
        email: String? = this.email,
        nickname: String? = this.nickname,
        profileImageUrl: String? = this.profileImageUrl
    ): User {
        return this.copy(
            email = email,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            updatedAt = LocalDateTime.now()
        )
    }
}
