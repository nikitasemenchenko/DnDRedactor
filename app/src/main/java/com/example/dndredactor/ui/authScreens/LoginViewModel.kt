package com.example.dndredactor.ui.authScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.data.AppConstants.emailError
import com.example.dndredactor.data.AppConstants.fieldFillingError
import com.example.dndredactor.data.AppConstants.unableToLogin
import com.example.dndredactor.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Waiting)
    val uiState = _uiState.asStateFlow()

    fun onLogin(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error(fieldFillingError)
            return
        } else if (!email.contains("@")) {
            _uiState.value = LoginUiState.Error(emailError)
        } else {
            viewModelScope.launch {
                _uiState.value = LoginUiState.Loading
                repository.login(email, password).onSuccess {
                    _uiState.value = LoginUiState.Success
                }
                    .onFailure {
                        _uiState.value = LoginUiState.Error(unableToLogin)

                    }
            }
        }
    }
}

sealed interface LoginUiState {
    object Waiting : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    data class Error(val message: String) : LoginUiState

}