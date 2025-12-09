package com.example.dndredactor.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.R
import com.example.dndredactor.data.CustomException
import com.example.dndredactor.data.repository.AuthRepository
import com.example.dndredactor.data.repository.CharacterRepository
import com.example.dndredactor.data.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val characterRepository: CharacterRepository,
    private val authRepository: AuthRepository,
    private val tokenStorage: TokenStorage
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainScreenUiState>(MainScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = MainScreenUiState.Loading

            characterRepository.getCharacters().onSuccess { characters ->
                _uiState.value = MainScreenUiState.Success(characters)
            }.onFailure { error ->

                if (tokenStorage.getAccessToken().isNullOrBlank() && tokenStorage.getRefreshToken().isNullOrBlank()) {
                    _uiState.value = MainScreenUiState.LoggedOut
                    return@onFailure
                }

                val errorId = if (error is CustomException) {
                    error.stringResId
                } else {
                    R.string.characterLoadError
                }

                _uiState.value = MainScreenUiState.Error(errorId)
            }
        }
    }

    fun deleteCharacter(id: String) {
        viewModelScope.launch {
            val current = uiState.value
            if (current !is MainScreenUiState.Success) return@launch
            val previous = current.characters
            val newList = previous.toMutableList().apply { removeAll { it.id == id } }
            _uiState.value = MainScreenUiState.Success(newList)

            characterRepository.deleteCharacter(id).onFailure {
                _uiState.value = MainScreenUiState.Success(previous)
            }
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.value = MainScreenUiState.LoggedOut
        }
    }
}