package com.storicard.domain.di

import com.storicard.datasource.repository.UserRepository
import com.storicard.domain.usecase.AddUserUseCase
import com.storicard.domain.usecase.AddUserUseCaseImpl
import com.storicard.domain.usecase.LoginUseCase
import com.storicard.domain.usecase.LoginUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  DomainModule {

    @Provides
    @Singleton
    fun provideAddUserUseCase(userRepository: UserRepository): AddUserUseCase {
        return AddUserUseCaseImpl(userRepository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase {
        return LoginUseCaseImpl(userRepository)
    }

}