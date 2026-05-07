package com.example.dndredactor.presentation.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.data.model.Archetype
import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.data.model.Subrace
import com.example.dndredactor.data.repository.CreationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreationViewModel(
) : ViewModel() {
    private val repository = CreationRepository()
    val _uiState = MutableStateFlow(CreationUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadRaces()
        loadClasses()
    }

    fun loadRaces() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, error = null)
            val races = repository.getRaces()
            _uiState.value = _uiState.value.copy(
                loading = false, races = races
            )
        }
    }

    fun loadClasses(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, error = null)
            val classes = repository.getClasses()
            _uiState.value = _uiState.value.copy(
                loading = false, classes = classes
            )
        }
    }

    fun onNameChanged(newName: String) {
        _uiState.value =
            _uiState.value.copy(character = _uiState.value.character.copy(fullName = newName))
    }

    fun onAppearanceChanged(appearance: String) {
        _uiState.value =
            _uiState.value.copy(character = _uiState.value.character.copy(appearance = appearance))
    }

    fun onCharacterChanged(character: String) {
        _uiState.value =
            _uiState.value.copy(character = _uiState.value.character.copy(character = character))
    }

    fun onIdealChanged(ideal: String) {
        _uiState.value =
            _uiState.value.copy(character = _uiState.value.character.copy(ideal = ideal))
    }

    fun onAttachmentChanged(attachment: String) {
        _uiState.value =
            _uiState.value.copy(character = _uiState.value.character.copy(attachment = attachment))
    }

    fun onWeaknessChanged(weakness: String) {
        _uiState.value =
            _uiState.value.copy(character = _uiState.value.character.copy(weakness = weakness))
    }

    fun onGenderSelected(gender: Gender) {
        _uiState.value =
            _uiState.value.copy(character = _uiState.value.character.copy(gender = gender))
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

    fun getRaceById(id: String?): Race? = _uiState.value.races.find { it.id == id }

    fun getSubraceById(race: Race, id: String?): Subrace? = race.subraces.find { it.id == id }


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

    fun getClassById(id: String?): CharacterClass? = _uiState.value.classes.find { it.id == id }

    fun getArchetypeById(characterClass: CharacterClass, id: String?): Archetype? = characterClass.archetypes.find { it.id == id }

    fun goToNextStep() {
        _uiState.value = _uiState.value.copy(
            currentStep = when (_uiState.value.currentStep) {
                CreationStep.RACE -> CreationStep.CLASS
                CreationStep.CLASS -> CreationStep.HUMAN_TRAITS
                CreationStep.HUMAN_TRAITS -> CreationStep.CHARACTERISTICS
                CreationStep.CHARACTERISTICS -> CreationStep.BACKGROUND
                CreationStep.BACKGROUND -> CreationStep.FINAL
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
                CreationStep.CHARACTERISTICS -> CreationStep.HUMAN_TRAITS
                CreationStep.BACKGROUND -> CreationStep.CHARACTERISTICS
                CreationStep.FINAL -> CreationStep.BACKGROUND
            }
        )
    }

    fun canGoToNextStep(): Boolean {
        val character = _uiState.value.character
        return when (_uiState.value.currentStep) {

            CreationStep.RACE -> character.fullName.isNotBlank() && character.gender != Gender.UNSPECIFIED && character.raceId != null

            CreationStep.CLASS -> character.classId != null && character.archetypeId != null

            CreationStep.HUMAN_TRAITS -> character.appearance.isNotBlank() && character.character.isNotBlank()
                    && character.ideal.isNotBlank() && character.attachment.isNotBlank() && character.weakness.isNotBlank()

            CreationStep.BACKGROUND -> true

            CreationStep.CHARACTERISTICS -> true

            CreationStep.FINAL -> true
        }
    }

}