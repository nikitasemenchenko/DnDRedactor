package com.example.dndredactor.ui.mainScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<MainScreenUiState>(MainScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun deleteCharacter(id: String) {
        _uiState.update { state ->
            if (state !is MainScreenUiState.Success) return@update state

            state.copy(
                characters = state.characters.filterNot { character ->
                    character.id == id
                }
            )
        }
    }
}