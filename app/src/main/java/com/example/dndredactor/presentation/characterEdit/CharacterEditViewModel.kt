package com.example.dndredactor.presentation.characterEdit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.Character
import com.example.dndredactor.domain.repository.LocalCharacterRepository
import com.example.dndredactor.presentation.navigation.AppRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localCharacterRepository: LocalCharacterRepository
): ViewModel() {
    private val route = savedStateHandle.toRoute<AppRoute.CharacterEdit>()
    val characterId = route.id

    private val _uiState = MutableStateFlow<CharacterEditUiState>(CharacterEditUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CharacterEditEvent>()
    val events = _events.asSharedFlow()

    init {
        loadCharacter()
    }

    private fun loadCharacter(){
        viewModelScope.launch {
            runCatching {
                localCharacterRepository.getCharacter(characterId).first()
            }.onSuccess { character ->
                _uiState.value = if (character == null) {
                    CharacterEditUiState.Error("Персонаж не найден")
                } else {
                    CharacterEditUiState.Success(character)
                }
            }.onFailure {
                _uiState.value = CharacterEditUiState.Error(
                    "Не удалось загрузить персонажа"
                )
            }
        }
    }

    fun onNameChanged(value: String) = updateCharacter {
        copy(
            name = value
        )
    }

    fun onBackstoryChanged(value: String) = updateCharacter {
        copy(backstory = value)
    }

    fun onEquipmentChanged(value: String) = updateCharacter {
        copy(equipment = value)
    }

    fun onAppearanceChanged(value: String) = updateCharacter {
        copy(appearance = value)
    }

    fun onPersonalityChanged(value: String) = updateCharacter {
        copy(personality = value)
    }

    fun onIdealChanged(value: String) = updateCharacter {
        copy(ideal = value)
    }

    fun onAttachmentChanged(value: String) = updateCharacter {
        copy(attachment = value)
    }

    fun onWeaknessChanged(value: String) = updateCharacter {
        copy(weakness = value)
    }

    fun increaseAbility(ability: Ability) {
        updateCharacter {
            val currentValue = abilityScores.get(ability)
            if (currentValue >= 20) return@updateCharacter this

            copy(
                abilityScores = abilityScores.set(
                    ability = ability,
                    value = currentValue + 1
                )
            )
        }
    }

    fun decreaseAbility(ability: Ability) {
        updateCharacter {
            val currentValue = abilityScores.get(ability)
            if (currentValue <= 1) return@updateCharacter this

            copy(
                abilityScores = abilityScores.set(
                    ability = ability,
                    value = currentValue - 1
                )
            )
        }
    }

    fun saveCharacter(){
        val state = _uiState.value as? CharacterEditUiState.Success ?: return
        val character = state.character

        if(character.name.isBlank() || character.personality.isBlank() || character.appearance.isBlank()){
            viewModelScope.launch {
                _events.emit(CharacterEditEvent.ShowError("Обязательные поля не заполнены"))
            }
            return
        }

        viewModelScope.launch {
            runCatching {
                localCharacterRepository.updateCharacter(character)
            }.onSuccess {
                _events.emit(CharacterEditEvent.CharacterUpdated)
            }.onFailure {
                _events.emit(CharacterEditEvent.ShowError("Не удалось сохранить изменения"))
                _uiState.value = CharacterEditUiState.Error(
                    "Не удалось сохранить изменения."
                )
            }
        }
    }

    private fun updateCharacter(
        action: Character.() -> Character
    ) {
        val state = _uiState.value as? CharacterEditUiState.Success ?: return

        _uiState.value = state.copy(
            character = state.character.action()
        )
    }
}

sealed interface CharacterEditEvent {
    data object CharacterUpdated : CharacterEditEvent

    data class ShowError(
        val message: String
    ) : CharacterEditEvent
}