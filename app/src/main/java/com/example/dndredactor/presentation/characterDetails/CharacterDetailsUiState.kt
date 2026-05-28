package com.example.dndredactor.presentation.characterDetails

import com.example.dndredactor.data.model.Character

sealed interface CharacterDetailsUiState {
    data object Loading: CharacterDetailsUiState

    data class Success(
        val character: Character
    ): CharacterDetailsUiState

    data class Error(
        val message: String
    ): CharacterDetailsUiState
}