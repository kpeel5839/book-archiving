package com.book.Archiving.infrastructure.client.apple

import com.fasterxml.jackson.annotation.JsonProperty

data class ApplePublicKeyResponse(
    val keys: List<ApplePublicKey>
)

data class ApplePublicKey(
    val kty: String,
    val kid: String,
    val use: String,
    val alg: String,
    val n: String,
    val e: String
)

data class AppleTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("token_type")
    val tokenType: String,

    @JsonProperty("expires_in")
    val expiresIn: Long,

    @JsonProperty("refresh_token")
    val refreshToken: String?,

    @JsonProperty("id_token")
    val idToken: String
)

data class AppleIdTokenPayload(
    val iss: String,
    val aud: String,
    val exp: Long,
    val iat: Long,
    val sub: String,
    val email: String?,

    @JsonProperty("email_verified")
    val emailVerified: String?,

    @JsonProperty("is_private_email")
    val isPrivateEmail: String?,

    @JsonProperty("auth_time")
    val authTime: Long?,

    @JsonProperty("nonce_supported")
    val nonceSupported: Boolean?
)
