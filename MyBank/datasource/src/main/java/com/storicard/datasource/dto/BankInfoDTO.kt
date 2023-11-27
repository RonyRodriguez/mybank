package com.storicard.datasource.dto

data class BankInfoDTO(
    val balance: Double = 0.0,
    val movementDTOS: List<MovementDTO> = emptyList()
)
