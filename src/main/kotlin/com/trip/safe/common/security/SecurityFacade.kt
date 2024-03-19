package com.trip.safe.common.security

import com.trip.safe.user.domain.User
import com.trip.safe.user.domain.UserRepository
import com.trip.safe.user.exception.UserNotFoundException
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityFacade(
    private val userRepository: UserRepository,
) {

    suspend fun getCurrentUser(): User {
        val accountId = ReactiveSecurityContextHolder.getContext().awaitSingle().authentication.name

        return userRepository.findByAccountId(accountId)
            ?: throw UserNotFoundException(UserNotFoundException.USER_NOT_FOUND)
    }
}
