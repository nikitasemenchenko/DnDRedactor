package com.example.dndredactor.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.data.AppConstants.loadError
import com.example.dndredactor.data.model.Character
import com.example.dndredactor.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            characterRepository.getCharacters().onSuccess { characters ->
                _uiState.value = MainUiState.Success(characters)
            }.onFailure { exception ->
                _uiState.value = MainUiState.Error(exception.message ?: loadError)
            }
        }
    }

    fun deleteCharacter(id: String) {
        viewModelScope.launch {
            val cur = (uiState.value as MainUiState.Success).characters.toMutableList()
            cur.removeAll { it.id == id }
            _uiState.value = MainUiState.Success(cur)

            characterRepository.deleteCharacter(id).onFailure {
                loadCharacters()
            }
        }
    }
}

sealed interface MainUiState {
    object Loading : MainUiState
    data class Success(val characters: List<Character>) : MainUiState
    data class Error(val message: String) : MainUiState
}