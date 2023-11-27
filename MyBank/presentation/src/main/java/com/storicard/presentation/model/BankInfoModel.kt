package com.storicard.presentation.model

import com.storicard.datasource.dto.BankInfoDTO

data class BankInfoModel(
    val balance: Double,
    val movementModels: List<MovementModel>
)

fun BankInfoModel.toBankInfoDTO(): BankInfoDTO {
    return BankInfoDTO(
        balance = this.balance,
        movementDTOS = this.movementModels.map { it.toMovementDTO() }
    )
}

fun BankInfoDTO.toBankInfoModel(): BankInfoModel {
    return BankInfoModel(
        balance = this.balance,
        movementModels = this.movementDTOS.map { it.toMovementModel() }
    )
}