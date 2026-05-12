package com.example.dndredactor.presentation.characterDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.dndredactor.data.model.Character
import com.example.dndredactor.domain.repository.CreationRepository
import com.example.dndredactor.domain.repository.LocalCharacterRepository
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
    private val creationRepository: CreationRepository
): ViewModel() {

    private val route = savedStateHandle.toRoute<AppRoute.CharacterDetails>()
    private val characterId = route.id

    val uiState = localCharacterRepository.getCharacter(characterId)
        .map{ character ->
            if(character == null) {
                CharacterDetailsUiState.Error("Персонаж не найден.")
            }
            else {
                toSuccessState(character)
            }
        }
        .catch {
            emit(CharacterDetailsUiState.Error("Произошла ошибка."))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CharacterDetailsUiState.Loading
        )

    fun toSuccessState(character: Character): CharacterDetailsUiState.Success  {
        val races = creationRepository.getRaces()
        val classes = creationRepository.getClasses()

        val race = races.find { it.id == character.raceId }
        val subrace = race?.subraces?.find { it.id == character.subraceId }

        val characterClass = classes.find { it.id == character.classId }
        val archetype = characterClass?.archetypes?.find { it.id == character.archetypeId }

        return CharacterDetailsUiState.Success(
            character = character,
            raceName = race?.name,
            subraceName = subrace?.name,
            className = characterClass?.name,
            archetypeName = archetype?.name
        )
    }
}