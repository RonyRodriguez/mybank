package com.storicard.mybank.domain

import android.net.Uri
import com.storicard.datasource.dto.UserDTO
import com.storicard.datasource.repository.UserRepository
import com.storicard.domain.usecase.AddUserUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AddUserUseCaseImplTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var addUserUseCase: AddUserUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testAddUser() {
        runBlocking {
            val user = UserDTO(
                userId = "12",
                email = "test12@example.com",
                password = "password1",
                name = "John",
                lastName = "Doe",
                photoUrl = ""
            )
            val uriString = "https://www.ejemplo.com/ruta/archivo.html"
            val uri = Uri.parse(uriString)
            addUserUseCase.addUser(user, uri)
            val userFromRepository = userRepository.getUser(user.userId)
            assertEquals(user, userFromRepository)
        }
    }
}