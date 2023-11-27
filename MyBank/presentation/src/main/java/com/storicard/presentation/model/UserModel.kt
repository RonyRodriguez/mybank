package com.storicard.presentation.model

import android.os.Parcelable
import com.storicard.datasource.dto.UserDTO

data class UserModel(
    var userId: String,
    val email: String,
    val password: String,
    val name: String,
    val lastName: String,
    val bankInfoModel: BankInfoModel,
    val photoUrl: String = ""
){
    fun getFullName(): String {
        return "$name $lastName"
    }
}

fun UserModel.toUserDTO(): UserDTO {
    return UserDTO(
        userId = this.userId,
        email = this.email,
        password = this.password,
        name = this.name,
        lastName = this.lastName,
        bankInfoDTO = this.bankInfoModel.toBankInfoDTO(),
        photoUrl = this.photoUrl
    )
}

fun UserDTO.toUserModel(): UserModel {
    return UserModel(
        userId = this.userId,
        email = this.email,
        password = this.password,
        name = this.name,
        lastName = this.lastName,
        bankInfoModel = this.bankInfoDTO.toBankInfoModel(),
        photoUrl = this.photoUrl
    )
}