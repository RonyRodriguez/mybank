package com.storicard.mybank.domain

import com.storicard.datasource.dto.BankInfoDTO
import com.storicard.datasource.dto.MovementDTO
import com.storicard.datasource.dto.UserDTO
import com.storicard.datasource.repository.UserRepository
import com.storicard.domain.usecase.LoginUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class LoginUseCaseImplTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testLogin() {
        runBlocking {
            val user = UserDTO(
                userId = "2",
                email = "test2@example.com",
                password = "password",
                name = "John",
                lastName = "Doe",
                bankInfoDTO = BankInfoDTO(
                    balance = 50.0,
                    movementDTOS = listOf(
                        MovementDTO("Deposit", 100.0),
                        MovementDTO("Withdrawal", -50.0)
                    )
                )
            )

            userRepository.addUser(user, null)

            val userFromRepository = userRepository.getUser(user.userId)
            assertEquals(user, userFromRepository)

            val loggedInUser = loginUseCase.login("test2@example.com", "password")
            assertEquals(user, loggedInUser)

            val wrongPasswordUser = loginUseCase.login("test2@example.com", "wrongpassword")
            assertNull(wrongPasswordUser)
        }
    }

}