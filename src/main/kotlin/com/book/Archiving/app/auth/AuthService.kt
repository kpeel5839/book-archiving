package com.book.Archiving.app.auth

import com.book.Archiving.domain.auth.SocialLoginClient
import com.book.Archiving.domain.auth.TokenProvider
import com.book.Archiving.domain.auth.TokenPair
import com.book.Archiving.domain.user.SocialType
import com.book.Archiving.domain.user.User
import com.book.Archiving.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val socialLoginClients: List<SocialLoginClient>,
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider
) {

    @Transactional
    fun socialLogin(provider: SocialType, accessToken: String): AuthResponse {
        val socialLoginClient = socialLoginClients.find { it.getProviderType() == provider }
            ?: throw IllegalArgumentException("Unsupported social provider: $provider")

        val socialUserInfo = socialLoginClient.getUserInfo(accessToken)

        val existingUser = userRepository.findBySocialIdAndSocialProvider(socialUserInfo.socialId, provider)

        val user = if (existingUser != null) {
            val updatedUser = existingUser.updateProfile(
                email = socialUserInfo.email,
                nickname = socialUserInfo.nickname,
                profileImageUrl = socialUserInfo.profileImageUrl
            )
            userRepository.save(updatedUser)
        } else {
            userRepository.save(
                User(
                    socialId = socialUserInfo.socialId,
                    socialProvider = provider,
                    email = socialUserInfo.email,
                    nickname = socialUserInfo.nickname,
                    profileImageUrl = socialUserInfo.profileImageUrl
                )
            )
        }

        val tokenPair = TokenPair(
            accessToken = tokenProvider.createAccessToken(user.id),
            refreshToken = tokenProvider.createRefreshToken(user.id)
        )

        return AuthResponse(
            userId = user.id,
            accessToken = tokenPair.accessToken,
            refreshToken = tokenPair.refreshToken,
            isNewUser = existingUser == null
        )
    }

    fun refreshToken(refreshToken: String): TokenPair {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw IllegalArgumentException("Invalid refresh token")
        }

        val userId = tokenProvider.getUserIdFromToken(refreshToken)
        userRepository.findById(userId)
            ?: throw IllegalArgumentException("User not found")

        return TokenPair(
            accessToken = tokenProvider.createAccessToken(userId),
            refreshToken = tokenProvider.createRefreshToken(userId)
        )
    }

}
