package com.example.dndredactor.presentation.characterEdit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.Archetype
import com.example.dndredactor.data.model.Character
import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.ClassType
import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.data.model.Subrace
import com.example.dndredactor.domain.repository.CreationRepository
import com.example.dndredactor.domain.repository.LocalCharacterRepository
import com.example.dndredactor.presentation.components.AppMessage
import com.example.dndredactor.presentation.navigation.AppRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class CharacterEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localCharacterRepository: LocalCharacterRepository,
    private val creationRepository: CreationRepository
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

    private fun loadCharacter() {
        viewModelScope.launch {
            runCatching {
                localCharacterRepository.getCharacter(characterId).first()
            }.onSuccess { character ->
                _uiState.value = if (character == null) {
                    CharacterEditUiState.Error(AppMessage.CharacterNotFound)
                } else {
                    CharacterEditUiState.Success(character)
                }
            }.onFailure {
                _uiState.value = CharacterEditUiState.Error(AppMessage.LoadCharacter)
            }
        }
    }

    fun onNameChanged(value: String) = updateCharacter {
        copy(
            name = value
        )
    }

    fun onGenderChanged(gender: Gender) = updateCharacter {
        copy(
            gender = gender
        )
    }

    fun increaseLevel() = updateCharacter {
        if(level >= MAX_CHARACTER_LEVEL) return@updateCharacter this
        copy(level = level+1)
    }

    fun decreaseLevel() = updateCharacter {
        if(level <= MIN_CHARACTER_LEVEL) return@updateCharacter this
        copy(level = level-1)
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

    fun loadRaceDetails(raceId: String) {
        val state = _uiState.value as? CharacterEditUiState.Success ?: return
        val currentRace = state.races.find { it.id == raceId }

        if (currentRace != null && currentRace.description.isNotBlank()) return

        viewModelScope.launch {
            val currentState = _uiState.value as? CharacterEditUiState.Success ?: return@launch

            _uiState.value = currentState.copy(
                raceDetailsLoading = true
            )

            runCatching {
                creationRepository.getRaceDetails(raceId)
            }.onSuccess { race ->
                val latestState = _uiState.value as? CharacterEditUiState.Success ?: return@launch
                _uiState.value = latestState.copy(
                    raceDetailsLoading = false,
                    races = latestState.races.map { currentRace ->
                        if (currentRace.id == raceId) race else currentRace
                    },
                    character = if (latestState.character.raceId == race.id) {
                        latestState.character.copy(
                            raceName = race.name
                        )
                    } else {
                        latestState.character
                    }
                )
            }.onFailure {
                val latestState = _uiState.value as? CharacterEditUiState.Success
                    ?: return@launch

                _uiState.value = latestState.copy(
                    raceDetailsLoading = false
                )

                _events.emit(CharacterEditEvent.ShowError(AppMessage.LoadRace))
            }
        }
    }

    fun onRaceSelected(race: Race) {
        val state = _uiState.value as? CharacterEditUiState.Success ?: return

        if (state.character.raceId != race.id) {
            _uiState.value = state.copy(
                character = state.character.copy(
                    raceId = race.id,
                    raceName = race.name,
                    subraceName = null,
                    subraceId = null
                )
            )
            loadRaceDetails(race.id)
        }
    }

    fun onSubraceSelected(subrace: Subrace) {
        val state = _uiState.value as? CharacterEditUiState.Success ?: return

        _uiState.value = state.copy(
            character = state.character.copy(
                subraceId = subrace.id,
                subraceName = subrace.name
            )
        )
    }

    fun loadClassDetails(classId: String) {
        val state = _uiState.value as? CharacterEditUiState.Success ?: return
        val currentClass = state.classes.find { it.id == classId }

        if (currentClass != null && currentClass.archetypes.isNotEmpty()) return

        viewModelScope.launch {
            val currentState = _uiState.value as? CharacterEditUiState.Success ?: return@launch

            _uiState.value = currentState.copy(
                classDetailsLoading = true
            )

            runCatching {
                creationRepository.getClassDetails(classId)
            }.onSuccess { detailedClass ->
                val latestState = _uiState.value as? CharacterEditUiState.Success ?: return@launch

                _uiState.value = latestState.copy(
                    classDetailsLoading = false,
                    classes = latestState.classes.map { currentClass ->
                        if (currentClass.id == classId) detailedClass else currentClass
                    },
                    character = if (latestState.character.classId == detailedClass.id) {
                        latestState.character.copy(
                            className = detailedClass.name,
                            classType = ClassType.fromApiId(detailedClass.id),
                        )
                    } else {
                        latestState.character
                    }
                )
            }.onFailure {
                val latestState = _uiState.value as? CharacterEditUiState.Success
                    ?: return@launch

                _uiState.value = latestState.copy(
                    classDetailsLoading = false
                )

                _events.emit(CharacterEditEvent.ShowError(AppMessage.LoadClass))
            }
        }
    }

    fun onClassSelected(characterClass: CharacterClass) {
        val state = _uiState.value as? CharacterEditUiState.Success ?: return

        if (state.character.classId != characterClass.id) {
            _uiState.value = state.copy(
                character = state.character.copy(
                    classId = characterClass.id,
                    className = characterClass.name,
                    classType = ClassType.fromApiId(characterClass.id),
                    archetypeName = null,
                    archetypeId = null
                )
            )
            loadClassDetails(characterClass.id)
        }
    }

    fun onArchetypeSelected(archetype: Archetype){
        val state = _uiState.value as? CharacterEditUiState.Success ?: return

        _uiState.value = state.copy(
            character = state.character.copy(
                archetypeId = archetype.id,
                archetypeName = archetype.name
            )
        )
    }

    fun increaseArmorClass() = updateCharacter {
        if (armorClass >= MAX_ARMOR_CLASS) return@updateCharacter this
        copy(armorClass = armorClass + 1)
    }

    fun decreaseArmorClass() = updateCharacter {
        if (armorClass <= MIN_ARMOR_CLASS) return@updateCharacter this
        copy(armorClass = armorClass - 1)
    }

    fun increaseMaxHitPoints() = updateCharacter {
        if (maxHitPoints >= MAX_HIT_POINTS) return@updateCharacter this

        copy(
            maxHitPoints = maxHitPoints + 1
        )
    }

    fun decreaseMaxHitPoints() = updateCharacter {
        if (maxHitPoints <= MIN_HIT_POINTS) return@updateCharacter this

        val nextMaxHitPoints = maxHitPoints - 1

        copy(
            maxHitPoints = nextMaxHitPoints,
            currentHitPoints = currentHitPoints.coerceAtMost(nextMaxHitPoints)
        )
    }

    fun increaseCurrentHitPoints() = updateCharacter {
        if (currentHitPoints >= maxHitPoints) return@updateCharacter this
        copy(currentHitPoints = currentHitPoints + 1)
    }

    fun decreaseCurrentHitPoints() = updateCharacter {
        if (currentHitPoints <= 0) return@updateCharacter this
        copy(currentHitPoints = currentHitPoints - 1)
    }

    fun onAdditionalInfoChanged(value: String) = updateCharacter {
        copy(additionalInfo = value)
    }

    fun onCoreEditClick() {
        val state = _uiState.value as? CharacterEditUiState.Success ?: return
        val enable = !state.coreEditEnabled

        _uiState.value = state.copy(
            coreEditEnabled = enable
        )

        if (enable == false) return

        if (state.races.isEmpty() || state.classes.isEmpty()) {
            loadCore()
        } else {
            loadSelectedCore()
        }
    }

    fun loadCore() {
        val state = _uiState.value as? CharacterEditUiState.Success ?: return
        viewModelScope.launch {
            _uiState.value = state.copy(
                coreLoading = true
            )

            runCatching {
                supervisorScope {
                    val racesAsync = async {
                        creationRepository.getRaces()
                    }

                    val classesAsync = async {
                        creationRepository.getClasses()
                    }

                    val races = racesAsync.await()
                    val classes = classesAsync.await()

                    races to classes
                }
            }.onSuccess { (races, classes) ->
                val currentState = _uiState.value as? CharacterEditUiState.Success ?: return@onSuccess

                _uiState.value = currentState.copy(
                    races = races,
                    classes = classes,
                    coreLoading = false
                )

                loadSelectedCore()
            }.onFailure {
                val currentState = _uiState.value as? CharacterEditUiState.Success ?: return@onFailure

                _uiState.value = currentState.copy(
                    coreLoading = false
                )

                _events.emit(
                    CharacterEditEvent.ShowError(AppMessage.LoadReferenceData)
                )
            }
        }
    }

    fun loadSelectedCore(){
        val state = _uiState.value as? CharacterEditUiState.Success ?: return
        state.character.raceId?.let {raceId ->
            loadRaceDetails(raceId)
        }
        state.character.classId?.let {classId ->
            loadClassDetails(classId)
        }
    }

    fun saveCharacter() {
        val state = _uiState.value as? CharacterEditUiState.Success ?: return
        val character = state.character

        if (state.coreLoading || state.raceDetailsLoading || state.classDetailsLoading) {
            viewModelScope.launch {
                _events.emit(CharacterEditEvent.ShowError(AppMessage.WaitForLoading))
            }
            return
        }

        val selectedRace = state.races.find {it.id == character.raceId}
        val selectedClass = state.classes.find {it.id == character.classId}

        if (selectedRace != null &&
            selectedRace.subraces.isNotEmpty() &&
            character.subraceId == null
        ) {
            viewModelScope.launch {
                _events.emit(CharacterEditEvent.ShowError(AppMessage.SelectSubrace))
            }
            return
        }

        if (selectedClass != null &&
            selectedClass.archetypes.isNotEmpty() &&
            character.archetypeId == null
        ) {
            viewModelScope.launch {
                _events.emit(CharacterEditEvent.ShowError(AppMessage.SelectArchetype))
            }
            return
        }

        if (character.name.isBlank() || character.gender == Gender.UNSPECIFIED ||
            character.level !in MIN_CHARACTER_LEVEL..MAX_CHARACTER_LEVEL) {
            viewModelScope.launch {
                _events.emit(CharacterEditEvent.ShowError(AppMessage.InvalidCharacter))
            }
            return
        }

        if (character.armorClass <= 0 ||
            character.maxHitPoints <= 0 ||
            character.currentHitPoints !in 0..character.maxHitPoints
        ) {
            viewModelScope.launch {
                _events.emit(CharacterEditEvent.ShowError(AppMessage.InvalidCombatStats))
            }
            return
        }

        viewModelScope.launch {
            runCatching {
                localCharacterRepository.updateCharacter(character)
            }.onSuccess {
                _events.emit(CharacterEditEvent.CharacterUpdated)
            }.onFailure {
                _events.emit(CharacterEditEvent.ShowError(AppMessage.UpdateCharacter))
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

    private companion object {
        const val MIN_CHARACTER_LEVEL = 1
        const val MAX_CHARACTER_LEVEL = 20

        const val MIN_ARMOR_CLASS = 1
        const val MAX_ARMOR_CLASS = 40

        const val MIN_HIT_POINTS = 1
        const val MAX_HIT_POINTS = 1000
    }
}

sealed interface CharacterEditEvent {
    data object CharacterUpdated : CharacterEditEvent

    data class ShowError(
        val message: AppMessage
    ) : CharacterEditEvent
}