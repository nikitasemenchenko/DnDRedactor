package com.example.dndredactor.presentation.characterDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.dndredactor.domain.repository.LocalCharacterRepository
import com.example.dndredactor.presentation.components.AppMessage
import com.example.dndredactor.presentation.navigation.AppRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localCharacterRepository: LocalCharacterRepository,
): ViewModel() {

    private val route = savedStateHandle.toRoute<AppRoute.CharacterDetails>()
    private val characterId = route.id

    val uiState = localCharacterRepository.getCharacter(characterId)
        .map{ character ->
            if(character == null) {
                CharacterDetailsUiState.Error(AppMessage.CharacterNotFound)
            }
            else {
                CharacterDetailsUiState.Success(character)
            }
        }
        .catch {
            emit(CharacterDetailsUiState.Error(AppMessage.Unknown))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CharacterDetailsUiState.Loading
        )
}