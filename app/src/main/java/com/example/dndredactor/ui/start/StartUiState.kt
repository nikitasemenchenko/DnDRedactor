package com.example.dndredactor.ui.start

import androidx.annotation.StringRes

sealed class StartUiState {
    object Loading : StartUiState()
    data class Error(@StringRes val message: Int) : StartUiState()
    object Authorized : StartUiState()
    object Unauthorized : StartUiState()
}