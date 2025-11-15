package com.example.dndredactor.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.data.repository.AuthRepository
import com.example.dndredactor.data.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StartViewModel(
    private val authRepository: AuthRepository,
    private val tokenStorage: TokenStorage
) : ViewModel() {
    private val _state = MutableStateFlow<StartState>(StartState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            checkAuth()
        }
    }

    private suspend fun checkAuth() {
        val refreshToken = tokenStorage.getRefreshToken()
        if (refreshToken.isNullOrBlank()) {
            _state.value = StartState.Unauthorized
            return
        }
        val result = authRepository.refresh()
        if (result.isSuccess) {
            _state.value = StartState.Authorized
        } else {
            _state.value = StartState.Unauthorized
        }
    }
}

sealed interface StartState {
    object Loading : StartState
    object Authorized : StartState
    object Unauthorized : StartState
}