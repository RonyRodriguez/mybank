package com.storicard.datasource.repository

import android.net.Uri
import com.storicard.datasource.dto.UserDTO

interface UserRepository {
    suspend fun login(email: String, password: String): UserDTO?
    suspend fun addUser(user: UserDTO, imageUri: Uri?)
    suspend fun getUser(userId: String): UserDTO?
    suspend fun updateUser(user: UserDTO)
    suspend fun deleteUser(userId: String)
    suspend fun addBatchUsers(users: List<UserDTO>)
}

