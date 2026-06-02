package com.example.dndredactor.presentation.characterDetails

import com.example.dndredactor.data.model.Character
import com.example.dndredactor.presentation.components.AppMessage

sealed interface CharacterDetailsUiState {
    data object Loading: CharacterDetailsUiState

    data class Success(
        val character: Character
    ): CharacterDetailsUiState

    data class Error(
        val message: AppMessage
    ): CharacterDetailsUiState
}