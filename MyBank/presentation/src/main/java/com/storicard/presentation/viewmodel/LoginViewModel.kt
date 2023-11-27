package com.storicard.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storicard.domain.usecase.LoginUseCase
import com.storicard.presentation.model.toUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val userDTO = loginUseCase.login(email, password)
                if (userDTO != null) {
                    _loginState.value = LoginState.Success(userDTO.toUserModel())
                } else {
                    _loginState.value = LoginState.Error("Login failed. Check your credentials.")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("An error occurred during login.")
            }
        }
    }
}