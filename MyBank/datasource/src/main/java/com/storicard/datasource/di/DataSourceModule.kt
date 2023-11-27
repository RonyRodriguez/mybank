package com.storicard.datasource.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.storicard.datasource.repository.FirebaseUserRepository
import com.storicard.datasource.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  DataSourceModule {

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideStorageReference(firebaseStorage: FirebaseStorage): StorageReference {
        return firebaseStorage.reference.child("images")
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideDatabaseReference(firebaseDatabase: FirebaseDatabase): DatabaseReference {
        return firebaseDatabase.getReference("users")
    }

    @Provides
    @Singleton
    fun provideUserRepository(databaseReference: DatabaseReference, storageReference: StorageReference): UserRepository {
        return FirebaseUserRepository(databaseReference,storageReference)
    }

}