package com.storicard.presentation.viewmodel

import com.storicard.presentation.model.UserModel

sealed class LoginState {
    data class Success(val user: UserModel) : LoginState()
    data class Error(val message: String) : LoginState()
}
