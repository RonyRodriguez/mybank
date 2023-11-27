package com.storicard.domain.usecase

import com.storicard.datasource.dto.UserDTO

interface LoginUseCase {
    suspend fun login(email: String, password: String): UserDTO?
}