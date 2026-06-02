package com.example.dndredactor.presentation.characterEdit

import com.example.dndredactor.data.model.Character
import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.presentation.components.AppMessage

sealed interface CharacterEditUiState {
    data object Loading: CharacterEditUiState

    data class Success(
        val character: Character,
        val races: List<Race> = emptyList(),
        val classes: List<CharacterClass> = emptyList(),
        val coreEditEnabled: Boolean = false,
        val coreLoading: Boolean = false,
        val raceDetailsLoading: Boolean = false,
        val classDetailsLoading: Boolean = false
    ): CharacterEditUiState

    data class Error(
        val message: AppMessage
    ): CharacterEditUiState
}