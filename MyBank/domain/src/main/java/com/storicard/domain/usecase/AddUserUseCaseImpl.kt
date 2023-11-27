package com.storicard.domain.usecase

import android.net.Uri
import com.storicard.datasource.dto.UserDTO
import com.storicard.datasource.repository.UserRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddUserUseCaseImpl @Inject constructor(private val userRepository: UserRepository) : AddUserUseCase {
    override suspend fun addUser(user: UserDTO, uri: Uri?) {
        userRepository.addUser(user, uri)
    }
}