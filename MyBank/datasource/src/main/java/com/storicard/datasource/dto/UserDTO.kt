package com.storicard.datasource.dto

data class UserDTO(
    val userId: String = "",
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val lastName: String = "",
    val bankInfoDTO: BankInfoDTO = BankInfoDTO(),
    val photoUrl: String = ""
)


