package com.example.dndredactor.ui.mainScreen

import androidx.annotation.StringRes
import com.example.dndredactor.data.model.CharacterPresentation

sealed class MainScreenUiState {
    object Loading : MainScreenUiState()
    data class Success(val characters: List<CharacterPresentation>) : MainScreenUiState()
    data class Error(@StringRes val message: Int) : MainScreenUiState()
    object LoggedOut : MainScreenUiState()
}
