package com.storicard.mybank.datasource

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.storicard.datasource.dto.BankInfoDTO
import com.storicard.datasource.dto.MovementDTO
import com.storicard.datasource.dto.UserDTO
import com.storicard.datasource.repository.FirebaseUserRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirebaseUserRepositoryTest {

    private lateinit var userRepository: FirebaseUserRepository
    private lateinit var databaseReference: DatabaseReference

    private lateinit var uri: Uri

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        FirebaseApp.initializeApp(appContext)

        val database = FirebaseDatabase.getInstance()
        val storage = FirebaseStorage.getInstance()

        databaseReference = database.getReference("test_users")
        userRepository = FirebaseUserRepository(
            databaseReference.child("users"),
            storage.reference.child("images")
        )

        val uriString = "https://www.ejemplo.com/ruta/archivo.html"
        uri = Uri.parse(uriString)
    }

    @After
    fun tearDown() {
        if (::databaseReference.isInitialized) {
            databaseReference.removeValue().addOnCompleteListener {

            }
        }
    }

    @Test
    fun testAddUser() = runBlocking {
        val userId = "123"
        val userEmail = "test@example.com"
        val userPassword = "password"
        val userName = "John"
        val userLastName = "Doe"

        val balance = 1000.0
        val movementList = listOf(
            MovementDTO("deposit", 500.0),
            MovementDTO("withdrawal", 200.0)
        )
        val bankInfoDTO = BankInfoDTO(balance, movementList)

        val user = UserDTO(userId, userEmail, userPassword, userName, userLastName, bankInfoDTO)
        userRepository.addUser(user, uri)
        println("User added successfully")
        println("User Details: $user")
        println("BankInfoDTO Details: $bankInfoDTO")

        val result = userRepository.getUser(userId)
        println("Result from getUser: $result")
        assertNotNull(result)
        assertEquals(userId, result?.userId)
        assertEquals(userEmail, result?.email)
        assertEquals(userName, result?.name)
        assertEquals(userLastName, result?.lastName)

        assertNotNull(result?.bankInfoDTO)
        assertEquals(balance, result?.bankInfoDTO?.balance)
        assertEquals(movementList.size, result?.bankInfoDTO?.movementDTOS?.size)
        assertEquals(movementList[0], result?.bankInfoDTO?.movementDTOS?.get(0))
        assertEquals(movementList[1], result?.bankInfoDTO?.movementDTOS?.get(1))
    }

    @Test
    fun testLoginWithLogs() = runBlocking {
        val userEmail = "test@example.com"
        val userPassword = "password"

        val user = UserDTO("658", userEmail, userPassword, "John", "Doe")

        userRepository.addUser(user, uri)

        println("Test Login: Attempting to log in with Email - $userEmail, Password - $userPassword")
        val loggedInUser = userRepository.login(userEmail, userPassword)

        println("Test Login: Result of login: $loggedInUser")
        assertNotNull(loggedInUser)
        assertEquals(userEmail, loggedInUser?.email)
        assertEquals(userPassword, loggedInUser?.password)

        val wrongPassword = "wrong_password"
        println("Test Login: Attempting to log in with wrong password: $wrongPassword")
        val wrongLoggedInUser = userRepository.login(userEmail, wrongPassword)

        println("Test Login: Result of login with wrong password: $wrongLoggedInUser")
        assertNull(wrongLoggedInUser)
    }


    @Test
    fun testGetNonExistentUser() = runBlocking {
        val result = userRepository.getUser("nonexistent_user_id")
        assertNull(result)
    }

    @Test
    fun testLogin() = runBlocking {
        val userEmail = "test@example.com"
        val userPassword = "password"

        val user = UserDTO("658", userEmail, userPassword, "John", "Doe")

        userRepository.addUser(user, uri)

        val loggedInUser = userRepository.login(userEmail, userPassword)

        assertNotNull(loggedInUser)
        assertEquals(userEmail, loggedInUser?.email)
        assertEquals(userPassword, loggedInUser?.password)

        val wrongPassword = "wrong_password"
        val wrongLoggedInUser = userRepository.login(userEmail, wrongPassword)

        assertNull(wrongLoggedInUser)
    }

    @Test
    fun testUpdateUser() = runBlocking {
        val originalUser = UserDTO("123", "test@example.com", "password", "John", "Doe")
        userRepository.addUser(originalUser, uri)

        val updatedUser = originalUser.copy(name = "UpdatedName", lastName = "UpdatedLastName")
        userRepository.updateUser(updatedUser)

        val result = userRepository.getUser(originalUser.userId)

        assertNotNull(result)
        assertEquals(originalUser.userId, result?.userId)
        assertEquals(updatedUser.name, result?.name)
        assertEquals(updatedUser.lastName, result?.lastName)
        assertEquals(originalUser.email, result?.email)
        assertEquals(originalUser.password, result?.password)
    }

    @Test
    fun testDeleteUser() = runBlocking {
        val userIdToDelete = "456"
        val userToDelete =
            UserDTO(userIdToDelete, "delete@example.com", "password", "ToDelete", "User")
        userRepository.addUser(userToDelete, uri)

        userRepository.deleteUser(userIdToDelete)

        val result = userRepository.getUser(userIdToDelete)

        assertNull(result)
    }

    @Test
    fun testAddBatchUsers() = runBlocking {
        val usersToAdd = listOf(
            UserDTO("1", "user1@example.com", "password1", "John", "Doe"),
            UserDTO("2", "user2@example.com", "password2", "Jane", "Doe"),
        )

        userRepository.addBatchUsers(usersToAdd)

        for (user in usersToAdd) {
            val result = userRepository.getUser(user.userId)
            assertNotNull(result)
            assertEquals(user, result)
        }
    }
}