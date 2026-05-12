package com.example.dndredactor.presentation.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.data.model.Archetype
import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.data.model.Subrace
import com.example.dndredactor.domain.repository.CreationRepository
import com.example.dndredactor.domain.repository.LocalCharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreationViewModel @Inject constructor(
    private val creationRepository: CreationRepository,
    private val localCharacterRepository: LocalCharacterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreationUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CreationEvent>()
    val events = _events.asSharedFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData(){
        _uiState.value = _uiState.value.copy(
            loading = false,
            races = creationRepository.getRaces(),
            classes = creationRepository.getClasses()
        )
    }

    fun onNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(fullName = newName)
        )
    }

    fun onAppearanceChanged(appearance: String) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(appearance = appearance)
        )
    }

    fun onCharacterChanged(character: String) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(character = character)
        )
    }

    fun onIdealChanged(ideal: String) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(ideal = ideal)
        )
    }

    fun onAttachmentChanged(attachment: String) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(attachment = attachment)
        )
    }

    fun onWeaknessChanged(weakness: String) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(weakness = weakness)
        )
    }

    fun onGenderSelected(gender: Gender) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(gender = gender)
        )
    }

    fun onRaceSelected(raceId: String?) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                raceId = raceId,
                subraceId = null
            )
        )
    }

    fun onSubraceSelected(subraceId: String?) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                subraceId = subraceId
            )
        )
    }

    fun onClassSelected(classId: String?) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                classId = classId,
                archetypeId = null
            )
        )
    }

    fun onArchetypeSelected(archetypeId: String?) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                archetypeId = archetypeId
            )
        )
    }

    fun getRaceById(id: String?): Race? =
        _uiState.value.races.find { it.id == id }

    fun getSubraceById(race: Race, id: String?): Subrace?
            = race.subraces.find { it.id == id }

    fun getClassById(id: String?): CharacterClass? =
        _uiState.value.classes.find { it.id == id }

    fun getArchetypeById(characterClass: CharacterClass, id: String?): Archetype? =
        characterClass.archetypes.find { it.id == id }


    fun goToNextStep() {
        _uiState.value = _uiState.value.copy(
            currentStep = when (_uiState.value.currentStep) {
                CreationStep.RACE -> CreationStep.CLASS
                CreationStep.CLASS -> CreationStep.HUMAN_TRAITS
                CreationStep.HUMAN_TRAITS -> CreationStep.FINAL
                CreationStep.FINAL -> CreationStep.FINAL
            }
        )
    }

    fun goToPreviousStep() {
        _uiState.value = _uiState.value.copy(
            currentStep = when (_uiState.value.currentStep) {
                CreationStep.RACE -> CreationStep.RACE
                CreationStep.CLASS -> CreationStep.RACE
                CreationStep.HUMAN_TRAITS -> CreationStep.CLASS
                CreationStep.FINAL -> CreationStep.HUMAN_TRAITS
            }
        )
    }

    fun canGoToNextStep(): Boolean {
        val character = _uiState.value.character

        return when (_uiState.value.currentStep) {
            CreationStep.RACE -> {
                character.fullName.isNotBlank() &&
                        character.gender != Gender.UNSPECIFIED &&
                        character.raceId != null
            }

            CreationStep.CLASS -> {
                character.classId != null &&
                        character.archetypeId != null
            }

            CreationStep.HUMAN_TRAITS -> {
                character.appearance.isNotBlank() &&
                        character.character.isNotBlank() &&
                        character.ideal.isNotBlank() &&
                        character.attachment.isNotBlank() &&
                        character.weakness.isNotBlank()
            }

            CreationStep.FINAL -> true
        }
    }

    fun saveCharacter() {
        val character = _uiState.value.character

        if (!canSaveCharacter()) {
            viewModelScope.launch {
                _events.emit(CreationEvent.ShowError("Заполните обязательные поля персонажа"))
            }
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)

            runCatching {
                localCharacterRepository.createCharacter(character)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(loading = false)
                _events.emit(CreationEvent.CharacterSaved)
            }.onFailure {
                _uiState.value = _uiState.value.copy(loading = false)
                _events.emit(CreationEvent.ShowError("Не удалось сохранить персонажа"))
            }
        }
    }

    private fun canSaveCharacter(): Boolean {
        val character = _uiState.value.character

        return character.fullName.isNotBlank() &&
                character.gender != Gender.UNSPECIFIED &&
                character.raceId != null &&
                character.classId != null &&
                character.archetypeId != null
    }

}

sealed interface CreationEvent {
    data object CharacterSaved : CreationEvent
    data class ShowError(val message: String) : CreationEvent
}