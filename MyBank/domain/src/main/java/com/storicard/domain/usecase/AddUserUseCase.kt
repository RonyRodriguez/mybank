package com.storicard.domain.usecase

import android.net.Uri
import com.storicard.datasource.dto.UserDTO

interface AddUserUseCase {
    suspend fun addUser(user: UserDTO, uri: Uri?)
}