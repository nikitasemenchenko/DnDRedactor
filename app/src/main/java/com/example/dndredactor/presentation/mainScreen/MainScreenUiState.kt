package com.example.dndredactor.presentation.mainScreen

import com.example.dndredactor.data.model.Character
import com.example.dndredactor.presentation.components.AppMessage

sealed interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data class Success(
        val characters: List<Character>
    ) : MainScreenUiState

    data class Error(
        val message: AppMessage
    ) : MainScreenUiState
}
