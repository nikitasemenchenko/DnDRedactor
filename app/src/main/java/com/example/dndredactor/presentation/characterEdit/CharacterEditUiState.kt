package com.example.dndredactor.presentation.characterEdit

import com.example.dndredactor.data.model.Character

sealed interface CharacterEditUiState {
    data object Loading: CharacterEditUiState

    data class Success(
        val character: Character
    ): CharacterEditUiState

    data class Error(
        val message: String
    ): CharacterEditUiState
}