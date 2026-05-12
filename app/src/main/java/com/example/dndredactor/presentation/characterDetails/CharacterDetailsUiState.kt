package com.example.dndredactor.presentation.characterDetails

import com.example.dndredactor.data.model.Character

sealed interface CharacterDetailsUiState {
    data object Loading: CharacterDetailsUiState

    data class Success(
        val character: Character,
        val raceName: String?,
        val subraceName: String?,
        val className: String?,
        val archetypeName: String?
    ): CharacterDetailsUiState

    data class Error(
        val message: String
    ): CharacterDetailsUiState
}