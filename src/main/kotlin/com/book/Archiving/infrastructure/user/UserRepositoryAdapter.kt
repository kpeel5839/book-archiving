package com.book.Archiving.infrastructure.user

import com.book.Archiving.domain.user.SocialType
import com.book.Archiving.domain.user.User
import com.book.Archiving.domain.user.UserRepository
import com.book.Archiving.infrastructure.user.jpa.UserJpaRepository
import com.book.Archiving.infrastructure.user.mapper.UserMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun save(user: User): User {
        val entity = UserMapper.toEntity(user)
        val savedEntity = userJpaRepository.save(entity)
        return UserMapper.toDomain(savedEntity)
    }

    override fun findById(id: Long): User? {
        return userJpaRepository.findByIdOrNull(id)?.let { UserMapper.toDomain(it) }
    }

    override fun findBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): User? {
        return userJpaRepository.findBySocialIdAndSocialProvider(socialId, socialProvider)
            ?.let { UserMapper.toDomain(it) }
    }

    override fun existsBySocialIdAndSocialProvider(socialId: String, socialProvider: SocialType): Boolean {
        return userJpaRepository.existsBySocialIdAndSocialProvider(socialId, socialProvider)
    }
}