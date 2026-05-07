package com.example.dndredactor.presentation.mainScreen

import com.example.dndredactor.data.model.CharacterPresentation

sealed interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data class Success(
        val characters: List<CharacterPresentation>
    ) : MainScreenUiState

    data class Error(
        val message: String
    ) : MainScreenUiState
}
