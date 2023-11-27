package com.storicard.presentation.viewmodel

import com.storicard.presentation.model.UserModel

sealed class AddUserState {
    object Loading : AddUserState()
    data class Success(val user: UserModel) : AddUserState()
    data class Error(val message: String) : AddUserState()
}
