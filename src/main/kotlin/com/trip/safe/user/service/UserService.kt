package com.trip.safe.user.service

import com.trip.safe.common.security.jwt.JwtTokenProvider
import com.trip.safe.user.domain.User
import com.trip.safe.user.domain.UserRepository
import com.trip.safe.user.exception.PasswordMisMatchException
import com.trip.safe.user.exception.UserExistException
import com.trip.safe.user.exception.UserNotFoundException
import com.trip.safe.user.presentation.dto.request.UserSignInRequest
import com.trip.safe.user.presentation.dto.request.UserSignUpRequest
import com.trip.safe.user.presentation.dto.response.TokenResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) {

    suspend fun signUp(request: UserSignUpRequest): TokenResponse {
        if (userRepository.existsByAccountId(request.accountId)) {
            throw UserExistException(UserExistException.USER_EXIST)
        }

        userRepository.save(
            User(
                accountId = request.accountId,
                password = passwordEncoder.encode(request.password),
                age = request.age,
            )
        )

        return jwtTokenProvider.getToken(request.accountId)
    }

    suspend fun signIn(request: UserSignInRequest): TokenResponse {
        val user = userRepository.findByAccountId(request.accountId)
            ?: throw UserNotFoundException(UserNotFoundException.USER_NOT_FOUND)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw PasswordMisMatchException(PasswordMisMatchException.PASSWORD_MIS_MATCH)
        }

        return jwtTokenProvider.getToken(request.accountId)
    }
}
