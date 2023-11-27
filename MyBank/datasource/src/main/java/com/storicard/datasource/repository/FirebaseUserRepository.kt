package com.storicard.datasource.repository

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.storicard.datasource.dto.UserDTO
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseUserRepository(
    private val usersReference: DatabaseReference,
    private val storageReference: StorageReference
) : UserRepository {

    override suspend fun addUser(user: UserDTO, imageUri: Uri?) {
        val userId = user.userId

        if (imageUri != null) {
            val storageRef = storageReference.child("user_images/$userId.jpg")
            val uploadTask = storageRef.putFile(imageUri)
            uploadTask.addOnSuccessListener { _ ->
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()

                    val userWithImageUrl = user.copy(photoUrl = imageUrl)

                    usersReference.child(userId).setValue(userWithImageUrl)
                }
            }.addOnFailureListener { e ->
                e.printStackTrace()
            }
        } else {
            usersReference.child(userId).setValue(user)
        }
    }

    override suspend fun login(email: String, password: String): UserDTO? {
        return try {
            val snapshot = usersReference.orderByChild("email").equalTo(email).get().await()
            if (snapshot.exists()) {
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserDTO::class.java)
                    if (user != null && user.password == password) {
                        return user
                    }
                }
            }

            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override suspend fun getUser(userId: String): UserDTO? {
        return try {
            val dataSnapshot = usersReference.child(userId).get().await()
            dataSnapshot.getValue(UserDTO::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun updateUser(user: UserDTO) {
        val userId = user.userId
        usersReference.child(userId).setValue(user)
    }

    override suspend fun deleteUser(userId: String) {
        usersReference.child(userId).removeValue()
    }

    override suspend fun addBatchUsers(users: List<UserDTO>) {
        val batchUpdateMap = mutableMapOf<String, Any?>()

        for (user in users) {
            val userId = user.userId
            batchUpdateMap[userId] = user
        }

        usersReference.updateChildren(batchUpdateMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //
                } else {
                    task.exception?.printStackTrace()
                }
            }
            .await()
    }

}
