package com.example.dndredactor.ui.creation

import com.example.dndredactor.data.model.NewCharacter
import com.example.dndredactor.data.model.Race

data class CreationUiState(
    val currentStep: CreationStep = CreationStep.RACE,
    val character: NewCharacter = NewCharacter(),
    val races: List<Race> = emptyList(),
    val loading: Boolean = false,
    val error: Int? = null
)

enum class CreationStep {
    RACE, CLASS, BACKGROUND, FINAL
}
