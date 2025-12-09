package com.example.dndredactor.ui.login

import androidx.annotation.StringRes

sealed class LoginUiState {
    object Waiting: LoginUiState()
    object Loading : LoginUiState()

    object Success : LoginUiState()

    data class Error(@StringRes val message: Int) : LoginUiState()
}

data class LoginInputState(
    val email: String = "",
    val password: String = "",
)