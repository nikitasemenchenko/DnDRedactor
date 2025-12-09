package com.example.dndredactor.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.R
import com.example.dndredactor.data.CustomException
import com.example.dndredactor.data.repository.AuthRepository
import com.example.dndredactor.data.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StartViewModel(
    private val authRepository: AuthRepository,
    private val tokenStorage: TokenStorage
) : ViewModel() {
    private val _state = MutableStateFlow<StartUiState>(StartUiState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            checkAuth()
        }
    }

    suspend fun checkAuth() {
        val refreshToken = tokenStorage.getRefreshToken()

        if (refreshToken.isNullOrBlank()) {
            _state.value = StartUiState.Unauthorized
            return
        }

        val result = authRepository.refresh()

        if (result.isSuccess) {
            _state.value = StartUiState.Authorized
        } else {
            val exception = result.exceptionOrNull()

            if (exception is CustomException) {
                when (exception.stringResId) {
                    R.string.internetError -> {
                        _state.value = StartUiState.Error(R.string.internetError)
                    }
                    else -> {
                        tokenStorage.clearAuthData()
                        _state.value = StartUiState.Unauthorized
                    }
                }
            } else {
                tokenStorage.clearAuthData()
                _state.value = StartUiState.Unauthorized
            }
        }
    }

    fun retryCheckAuth() {
        viewModelScope.launch {
            _state.value = StartUiState.Loading
            checkAuth()
        }
    }
}