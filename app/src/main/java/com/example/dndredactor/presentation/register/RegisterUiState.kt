package com.example.dndredactor.presentation.register

import androidx.annotation.StringRes

sealed class RegisterUiState {
    object Waiting : RegisterUiState()

    object Loading : RegisterUiState()

    object Success : RegisterUiState()

    data class Error(@StringRes val message: Int) : RegisterUiState()
}

data class RegisterInputState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val repeatedPassword: String = ""
)
