package com.storicard.domain.usecase

import com.storicard.datasource.dto.UserDTO
import com.storicard.datasource.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCaseImpl @Inject constructor(private val userRepository: UserRepository) : LoginUseCase {
    override suspend fun login(email: String, password: String): UserDTO? {
        return userRepository.login(email, password)
    }
}