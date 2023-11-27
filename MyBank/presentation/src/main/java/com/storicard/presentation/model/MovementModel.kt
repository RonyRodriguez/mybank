package com.storicard.presentation.model

import com.storicard.datasource.dto.MovementDTO

data class MovementModel(
    val description: String,
    val amount: Double
)

fun MovementDTO.toMovementModel(): MovementModel {
    return MovementModel(
        description = this.description,
        amount = this.amount
    )
}

fun MovementModel.toMovementDTO(): MovementDTO {
    return MovementDTO(
        description = this.description,
        amount = this.amount
    )
}