package com.example.dndredactor.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.domain.repository.LocalCharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val characterRepository: LocalCharacterRepository
) : ViewModel() {

    val uiState = characterRepository.getCharacters()
        .map { characters ->
            MainScreenUiState.Success(characters) as MainScreenUiState
        }
        .catch {
            emit(MainScreenUiState.Error(""))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainScreenUiState.Loading
        )

    fun deleteCharacter(id: Int) {
        viewModelScope.launch {
            characterRepository.deleteCharacter(id)
        }
    }
}