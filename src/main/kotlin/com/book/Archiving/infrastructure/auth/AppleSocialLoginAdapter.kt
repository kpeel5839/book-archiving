package com.book.Archiving.infrastructure.auth

import com.book.Archiving.domain.auth.SocialLoginClient
import com.book.Archiving.domain.auth.SocialUserInfo
import com.book.Archiving.domain.user.SocialType
import com.book.Archiving.infrastructure.auth.feign.apple.AppleAuthFeignClient
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.*

@Component
class AppleSocialLoginAdapter(
    private val appleAuthFeignClient: AppleAuthFeignClient,
    private val objectMapper: ObjectMapper
) : SocialLoginClient {

    override fun getProviderType(): SocialType = SocialType.APPLE

    override fun getUserInfo(accessToken: String): SocialUserInfo {
        val idToken = accessToken
        val payload = verifyAndDecodeIdToken(idToken)

        return SocialUserInfo(
            socialId = payload.sub,
            email = payload.email,
            nickname = null,
            profileImageUrl = null
        )
    }

    private fun verifyAndDecodeIdToken(idToken: String): AppleIdTokenPayload {
        val publicKeys = appleAuthFeignClient.getPublicKeys()
        val headerJson = String(Base64.getUrlDecoder().decode(idToken.split(".")[0]))
        val header = objectMapper.readValue(headerJson, Map::class.java)
        val kid = header["kid"] as String

        val matchingKey = publicKeys.keys.find { it.kid == kid }
            ?: throw IllegalArgumentException("Matching public key not found")

        val publicKey = generatePublicKey(matchingKey.n, matchingKey.e)

        val claims = Jwts.parser()
            .verifyWith(publicKey)
            .build()
            .parseSignedClaims(idToken)
            .payload

        return AppleIdTokenPayload(
            sub = claims.subject,
            email = claims["email"] as? String
        )
    }

    private fun generatePublicKey(n: String, e: String): PublicKey {
        val nBytes = Base64.getUrlDecoder().decode(n)
        val eBytes = Base64.getUrlDecoder().decode(e)

        val modulus = BigInteger(1, nBytes)
        val exponent = BigInteger(1, eBytes)

        val spec = RSAPublicKeySpec(modulus, exponent)
        val factory = KeyFactory.getInstance("RSA")
        return factory.generatePublic(spec)
    }
}
