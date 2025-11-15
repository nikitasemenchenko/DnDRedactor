package com.example.dndredactor.ui.authScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.data.AppConstants.emailError
import com.example.dndredactor.data.AppConstants.failedToRegister
import com.example.dndredactor.data.AppConstants.fieldFillingError
import com.example.dndredactor.data.AppConstants.passwordError
import com.example.dndredactor.data.AppConstants.passwordMatchError
import com.example.dndredactor.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Waiting)
    val uiState = _uiState.asStateFlow()

    fun onRegister(
        username: String,
        email: String,
        firstPassword: String,
        secondPassword: String
    ) {
        if (username.isBlank() || email.isBlank()
            || firstPassword.isBlank() || secondPassword.isBlank()
        ) {
            _uiState.value = RegisterUiState.Error(fieldFillingError)
            return
        } else if (!email.contains("@")) {
            _uiState.value = RegisterUiState.Error(emailError)
            return
        } else if (firstPassword != secondPassword) {
            _uiState.value = RegisterUiState.Error(passwordMatchError)
            return
        } else if (!checkPassword(firstPassword)) {
            _uiState.value = RegisterUiState.Error(passwordError)
            return
        } else {
            viewModelScope.launch {
                _uiState.value = RegisterUiState.Loading
                repository.registration(username, email, firstPassword)
                    .onSuccess {
                        _uiState.value = RegisterUiState.Success
                    }
                    .onFailure {
                        _uiState.value = RegisterUiState.Error(failedToRegister)
                    }
            }
        }
    }

    fun checkPassword(password: String): Boolean {
        if (password.length < 8 || password.length > 255) return false
        if (!password.any { it.isDigit() }) return false
        if (!password.any { it.isUpperCase() }) return false
        if (!password.any { it in "~!?@#$%^&*_-+()[]{}></\\|\"'.,:;" }) return false
        return true
    }

}

sealed interface RegisterUiState {
    object Waiting : RegisterUiState
    object Loading : RegisterUiState
    object Success : RegisterUiState
    data class Error(val message: String) : RegisterUiState

}