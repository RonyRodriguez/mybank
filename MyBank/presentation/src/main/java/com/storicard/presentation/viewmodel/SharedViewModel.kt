package com.storicard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.storicard.presentation.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _userData = MutableStateFlow<UserModel?>(null)
    val userData: StateFlow<UserModel?> = _userData

    fun setUser(user: UserModel) {
        _userData.value = user
    }
}