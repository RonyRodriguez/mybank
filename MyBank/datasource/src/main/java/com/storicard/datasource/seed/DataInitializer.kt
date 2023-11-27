package com.storicard.datasource.seed

import android.util.Log
import com.google.firebase.database.*
import com.storicard.datasource.dto.BankInfoDTO
import com.storicard.datasource.dto.MovementDTO

class DataInitializer(private val dataNode: String) {

    fun initializeDataIfEmpty() {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference(dataNode)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    preloadInitialData(reference)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                databaseError.toException().printStackTrace()
            }
        })
    }

    private fun preloadInitialData(reference: DatabaseReference) {
        val initialData = mapOf(
            "user1" to createUserDTO("user1@example.com", "password1", "John", "Doe", 1000.0),
            "user2" to createUserDTO("user2@example.com", "password2", "Jane", "Doe", 1500.0),
        )

        reference.updateChildren(initialData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("DataInitializer", "Datos iniciales precargados exitosamente.")
                } else {
                    task.exception?.printStackTrace()
                }
            }
    }

    private fun createUserDTO(
        email: String,
        password: String,
        name: String,
        lastName: String,
        initialBalance: Double
    ): Map<String, Any?> {
        val movementList = listOf(
            MovementDTO("Depósito inicial", initialBalance),
        )

        val bankInfoDTO = BankInfoDTO(initialBalance, movementList)

        return mapOf(
            "userId" to generateRandomUserId(),
            "email" to email,
            "password" to password,
            "name" to name,
            "lastName" to lastName,
            "bankInfoDTO" to bankInfoDTO
        )
    }

    private fun generateRandomUserId(): String {
        return "user_${System.currentTimeMillis()}"
    }
}