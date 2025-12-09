package com.example.dndredactor.ui.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.data.model.Subrace
import com.example.dndredactor.data.repository.CreationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreationViewModel(
    private val repository: CreationRepository
): ViewModel() {
    val _uiState = MutableStateFlow(CreationUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadRaces()
    }

    fun loadRaces(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, error = null)
            val races = repository.getRaces()
                _uiState.value = _uiState.value.copy(
                    loading = false, races = races)
            }
        }

    fun onNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(character = _uiState.value.character.copy(fullName = newName))
    }

    fun onGenderSelected(gender: Gender) {
        _uiState.value = _uiState.value.copy(character = _uiState.value.character.copy(gender = gender))
    }

    fun onRaceSelected(raceId: String?) {
        val race = _uiState.value.races.find { it.id == raceId }
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                raceId = raceId,
                subraceId = null,
                raceDescription = race?.description
            )
        )
    }

    fun onSubraceSelected(subraceId: String?) {
        val selectedRace = _uiState.value.races.find { it.id == _uiState.value.character.raceId }
        val selectedSubrace = selectedRace?.subraces?.find { it.id == subraceId }
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                subraceId = subraceId,
                subraceDescription = selectedSubrace?.description
            )
        )
    }

    fun canGoToClassSelection(): Boolean{
        val character = _uiState.value.character
        return character.fullName.isNotBlank() && character.gender != Gender.UNSPECIFIED && character.raceId != null
    }

    fun getRaceById(id: String?): Race? = _uiState.value.races.find { it.id == id }

    fun getSubraceById(race: Race, id: String?): Subrace? = race.subraces.find { it.id == id }

    fun goToNextStep() {
        _uiState.value = _uiState.value.copy(
            currentStep = when (_uiState.value.currentStep) {
                CreationStep.RACE -> CreationStep.CLASS
                CreationStep.CLASS -> CreationStep.BACKGROUND
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
                CreationStep.BACKGROUND -> CreationStep.CLASS
                CreationStep.FINAL -> CreationStep.BACKGROUND
            }
        )
    }

    fun canGoToNextStep(): Boolean {
        return when (_uiState.value.currentStep) {
            CreationStep.RACE -> canGoToClassSelection()
            CreationStep.CLASS -> true
            CreationStep.BACKGROUND -> true
            CreationStep.FINAL -> true
        }
    }

}