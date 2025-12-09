package com.example.dndredactor.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.R
import com.example.dndredactor.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Waiting)
    val uiState = _uiState.asStateFlow()

    private val _inputState = MutableStateFlow(RegisterInputState())
    val inputState = _inputState.asStateFlow()

    fun onEmailChanged(newEmail: String){
        _inputState.update { inputState ->
            inputState.copy(email = newEmail)
        }
    }

    fun onUsernameChanged(newUsername: String){
        _inputState.update { inputState ->
            inputState.copy(username = newUsername)
        }
    }

    fun onPasswordChanged(newPassword: String){
        _inputState.update { inputState ->
            inputState.copy(password = newPassword)
        }
    }

    fun onRepeatPasswordChanged(newRepeatPassword: String){
        _inputState.update { inputState ->
            inputState.copy(repeatedPassword = newRepeatPassword)
        }
    }

    fun onRegister() {
        if (_inputState.value.username.isBlank() || _inputState.value.email.isBlank()
            || _inputState.value.password.isBlank() || _inputState.value.repeatedPassword.isBlank()
        ) {
            _uiState.value = RegisterUiState.Error(R.string.fieldFillingError)
            return
        } else if (!_inputState.value.email.contains("@")) {
            _uiState.value = RegisterUiState.Error(R.string.emailError)
            return
        } else if (_inputState.value.password != _inputState.value.repeatedPassword) {
            _uiState.value = RegisterUiState.Error(R.string.passwordMatchError)
            return
        } else if (!checkPassword(_inputState.value.password)) {
            _uiState.value = RegisterUiState.Error(R.string.passwordError)
            return
        } else {
            viewModelScope.launch {
                _uiState.value = RegisterUiState.Loading
                repository.registration(_inputState.value.username, _inputState.value.email, _inputState.value.password)
                    .onSuccess {
                        _uiState.value = RegisterUiState.Success
                    }
                    .onFailure { error ->
                        _uiState.value = RegisterUiState.Error(R.string.failedToRegister)
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