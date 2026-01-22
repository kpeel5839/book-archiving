package com.book.Archiving.infrastructure.user.jpa

import com.book.Archiving.domain.user.SocialType
import com.book.Archiving.infrastructure.user.jpa.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    fun findBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): UserEntity?

    fun existsBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): Boolean

}
