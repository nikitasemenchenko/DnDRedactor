package com.example.dndredactor.presentation.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.AbilityGenerationMethod
import com.example.dndredactor.data.model.AbilityScores
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
import kotlin.random.Random

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

    fun onPersonalityChanged(personality: String) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(personality = personality)
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

    fun getRemainingPoints(): Int {
        val scores = _uiState.value.character.abilityScores

        val spent = Ability.entries.sumOf { ability ->
            POINT_BUY_COST[scores.get(ability)] ?: 0
        }

        return POINT_BUY_BUDGET - spent
    }

    fun increaseAbility(ability: Ability) {
        val currentScores = _uiState.value.character.abilityScores
        val currentValue = currentScores.get(ability)

        if (currentValue >= MAX_POINT_BUY_SCORE) return

        val nextValue = currentValue+1
        val currentCost = POINT_BUY_COST[currentValue] ?: return
        val nextCost = POINT_BUY_COST[nextValue] ?: return
        val diff = nextCost - currentCost

        if(getRemainingPoints() < diff) return

        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                abilityScores = currentScores.set(
                    ability = ability,
                    value = nextValue
                )
            )
        )
    }

    fun decreaseAbility(ability: Ability) {
        val currentScores = _uiState.value.character.abilityScores
        val currentValue = currentScores.get(ability)

        if (currentValue <= MIN_POINT_BUY_SCORE) return

        val nextValue = currentValue - 1

        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                abilityScores = currentScores.set(
                    ability = ability,
                    value = nextValue
                )
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
                CreationStep.HUMAN_TRAITS -> CreationStep.ABILITY_GENERATION_METHOD
                CreationStep.ABILITY_GENERATION_METHOD -> {
                    when (_uiState.value.character.abilityGenerationMethod) {
                        AbilityGenerationMethod.RANDOM -> CreationStep.RANDOM_ABILITIES
                        AbilityGenerationMethod.POINT_BUY -> CreationStep.POINT_BUY_ABILITIES
                        else -> CreationStep.ABILITY_GENERATION_METHOD
                    }
                }
                CreationStep.RANDOM_ABILITIES -> CreationStep.FINAL
                CreationStep.POINT_BUY_ABILITIES -> CreationStep.FINAL
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
                CreationStep.ABILITY_GENERATION_METHOD -> CreationStep.HUMAN_TRAITS
                CreationStep.RANDOM_ABILITIES -> CreationStep.ABILITY_GENERATION_METHOD
                CreationStep.POINT_BUY_ABILITIES -> CreationStep.ABILITY_GENERATION_METHOD
                CreationStep.FINAL -> {
                    when (_uiState.value.character.abilityGenerationMethod) {
                        AbilityGenerationMethod.RANDOM -> CreationStep.RANDOM_ABILITIES
                        AbilityGenerationMethod.POINT_BUY -> CreationStep.POINT_BUY_ABILITIES
                        else -> CreationStep.ABILITY_GENERATION_METHOD
                    }
                }
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
                        character.personality.isNotBlank() &&
                        character.ideal.isNotBlank() &&
                        character.attachment.isNotBlank() &&
                        character.weakness.isNotBlank()
            }

            CreationStep.ABILITY_GENERATION_METHOD -> {
                _uiState.value.character.abilityGenerationMethod != null
            }

            CreationStep.RANDOM_ABILITIES -> true

            CreationStep.POINT_BUY_ABILITIES -> {
                getRemainingPoints() >= 0
            }

            CreationStep.FINAL -> true
        }
    }

    fun generateScores(): AbilityScores {
        return AbilityScores(
            strength = rollAbilityScore(),
            dexterity = rollAbilityScore(),
            constitution = rollAbilityScore(),
            intelligence = rollAbilityScore(),
            wisdom = rollAbilityScore(),
            charisma = rollAbilityScore()
        )
    }

    fun rollAbilityScore(): Int{
        return List(4) {
            Random.nextInt(from = 1, until = 7)
        }
            .sortedDescending()
            .take(3)
            .sum()
    }

    fun regenerateScores(){
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                abilityScores = generateScores()
            )
        )
    }

    fun onAbilityGenerationMethodSelected(method: AbilityGenerationMethod) {
        val newScores = when(method) {
            AbilityGenerationMethod.POINT_BUY -> AbilityScores()
            AbilityGenerationMethod.RANDOM -> generateScores()
        }

        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                abilityGenerationMethod = method,
                abilityScores = newScores
            )
        )
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
    private companion object {
        const val POINT_BUY_BUDGET = 27
        const val MIN_POINT_BUY_SCORE = 8
        const val MAX_POINT_BUY_SCORE = 15

        val POINT_BUY_COST = mapOf(
            8 to 0,
            9 to 1,
            10 to 2,
            11 to 3,
            12 to 4,
            13 to 5,
            14 to 7,
            15 to 9
        )
    }
}

sealed interface CreationEvent {
    data object CharacterSaved : CreationEvent
    data class ShowError(val message: String) : CreationEvent
}