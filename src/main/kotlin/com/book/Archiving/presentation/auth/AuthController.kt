package com.book.Archiving.presentation.auth

import com.book.Archiving.app.auth.AuthService
import com.book.Archiving.app.auth.request.RefreshTokenRequest
import com.book.Archiving.app.auth.request.SocialLoginRequest
import com.book.Archiving.app.auth.request.SocialLoginResponse
import com.book.Archiving.app.auth.request.TokenResponse
import com.book.Archiving.presentation.auth.swagger.AuthControllerSwagger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) : AuthControllerSwagger {

    @PostMapping("/login")
    override fun socialLogin(@RequestBody request: SocialLoginRequest): ResponseEntity<SocialLoginResponse> {
        val result = authService.socialLogin(request.socialType, request.accessToken)

        return ResponseEntity.ok(
            SocialLoginResponse(
                userId = result.userId,
                accessToken = result.accessToken,
                refreshToken = result.refreshToken,
                isNewUser = result.isNewUser
            )
        )
    }

    @PostMapping("/refresh")
    override fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<TokenResponse> {
        val result = authService.refreshToken(request.refreshToken)

        return ResponseEntity.ok(
            TokenResponse(
                accessToken = result.accessToken,
                refreshToken = result.refreshToken
            )
        )
    }
}
