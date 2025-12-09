package com.example.dndredactor.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.R
import com.example.dndredactor.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Waiting)
    val uiState = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(LoginInputState())
    val inputState = _inputState.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _inputState.update { inputState ->
            inputState.copy(email = newEmail)
        }
    }

    fun onPasswordChanged(newPassword: String) {
        _inputState.update { inputState ->
            inputState.copy(password = newPassword)
        }
    }

    fun onLogin() {
        if (_inputState.value.email.isBlank() || _inputState.value.password.isBlank()) {
            _uiState.value = LoginUiState.Error(R.string.fieldFillingError)
            return
        } else if (!_inputState.value.email.contains("@")) {
            _uiState.value = LoginUiState.Error(R.string.emailError)
            return
        } else {
            viewModelScope.launch {
                _uiState.value = LoginUiState.Loading
                repository.login(_inputState.value.email, _inputState.value.password).onSuccess {
                    _uiState.value = LoginUiState.Success
                }
                    .onFailure {
                        _uiState.value = LoginUiState.Error(R.string.unableToLogin)

                    }
            }
        }
    }
}