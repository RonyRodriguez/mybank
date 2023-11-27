package com.storicard.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.storicard.domain.usecase.AddUserUseCase
import com.storicard.presentation.model.UserModel
import com.storicard.presentation.model.toUserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel  @Inject constructor(
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {

    private val _addUserState = MutableLiveData<AddUserState>()
    val addUserState: LiveData<AddUserState>
        get() = _addUserState

    fun addUser(userModel: UserModel, uri: Uri?) {
        viewModelScope.launch {
            try {
                userModel.userId = UUID.randomUUID().toString()
                val userDTO = userModel.toUserDTO()
                addUserUseCase.addUser(userDTO, uri)
                _addUserState.value = AddUserState.Success(userModel)
            } catch (e: Exception) {
                _addUserState.value = AddUserState.Error("An error occurred during user addition.")
            }
        }
    }
}