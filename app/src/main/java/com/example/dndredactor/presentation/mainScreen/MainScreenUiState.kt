package com.example.dndredactor.presentation.mainScreen

import com.example.dndredactor.data.model.Character

sealed interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data class Success(
        val characters: List<Character>
    ) : MainScreenUiState

    data class Error(
        val message: String
    ) : MainScreenUiState
}
