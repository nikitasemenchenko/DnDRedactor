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
import com.example.dndredactor.domain.repository.usecase.GetCreationDataUseCase
import com.example.dndredactor.presentation.components.AppMessage
import com.example.dndredactor.presentation.creation.logic.AbilityScoreGenerator.generateScores
import com.example.dndredactor.presentation.creation.logic.CreationLimits.MAX_ARMOR_CLASS
import com.example.dndredactor.presentation.creation.logic.CreationLimits.MAX_CHARACTER_LEVEL
import com.example.dndredactor.presentation.creation.logic.CreationLimits.MAX_HIT_POINTS
import com.example.dndredactor.presentation.creation.logic.CreationLimits.MAX_POINT_BUY_SCORE
import com.example.dndredactor.presentation.creation.logic.CreationLimits.MIN_ARMOR_CLASS
import com.example.dndredactor.presentation.creation.logic.CreationLimits.MIN_CHARACTER_LEVEL
import com.example.dndredactor.presentation.creation.logic.CreationLimits.MIN_HIT_POINTS
import com.example.dndredactor.presentation.creation.logic.CreationLimits.MIN_POINT_BUY_SCORE
import com.example.dndredactor.presentation.creation.logic.CreationStepNavigator
import com.example.dndredactor.presentation.creation.logic.CreationValidator
import com.example.dndredactor.presentation.creation.logic.PointBuyRules
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
    private val localCharacterRepository: LocalCharacterRepository,
    private val getCreationDataUseCase: GetCreationDataUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreationUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CreationEvent>()
    val events = _events.asSharedFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                loading = true,
                error = null
            )

            runCatching {
                getCreationDataUseCase()
            }.onSuccess { data ->
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    races = data.races,
                    classes = data.classes,
                    error = null
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = AppMessage.LoadReferenceData
                )

                _events.emit(
                    CreationEvent.ShowError(AppMessage.LoadReferenceData)
                )
            }
        }
    }

    fun retryInitialDataLoading(){
        loadInitialData()
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

    fun onRaceSelected(race: Race) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                raceId = race.id,
                raceName = race.name,
                subraceId = null,
                subraceName = null
            )
        )

        loadRaceDetails(race.id)
    }

    fun loadRaceDetails(raceId: String) {
        val currentRace = getRaceById(raceId)

        if (currentRace != null && currentRace.description.isNotBlank()) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                raceDetailsLoading = true
            )

            runCatching {
                creationRepository.getRaceDetails(raceId)
            }.onSuccess { detailedRace ->
                val currentCharacter = _uiState.value.character

                _uiState.value = _uiState.value.copy(
                    raceDetailsLoading = false,
                    races = replaceRace(_uiState.value.races, detailedRace),
                    character = if (currentCharacter.raceId == detailedRace.id) {
                        currentCharacter.copy(
                            raceName = detailedRace.name
                        )
                    } else {
                        currentCharacter
                    }
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    raceDetailsLoading = false
                )

                _events.emit(CreationEvent.ShowError(AppMessage.LoadRace))
            }
        }
    }

    fun loadClassDetails(classId: String) {
        val currentClass = getClassById(classId)

        if (currentClass != null && currentClass.archetypes.isNotEmpty()) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                classDetailsLoading = true
            )

            runCatching {
                creationRepository.getClassDetails(classId)
            }.onSuccess { detailedClass ->
                val currentCharacter = _uiState.value.character

                _uiState.value = _uiState.value.copy(
                    classDetailsLoading = false,
                    classes = replaceClass(_uiState.value.classes, detailedClass),
                    character = if (currentCharacter.classId == detailedClass.id) {
                        currentCharacter.copy(
                            className = detailedClass.name
                        )
                    } else {
                        currentCharacter
                    }
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    classDetailsLoading = false
                )

                _events.emit(CreationEvent.ShowError(AppMessage.LoadClass))
            }
        }
    }

    fun replaceRace(races: List<Race>, race: Race): List<Race> {
        return races.map { curRace ->
            if (curRace.id == race.id) race else curRace
        }
    }

    fun replaceClass(
        classes: List<CharacterClass>,
        characterClass: CharacterClass
    ): List<CharacterClass> {
        return classes.map { curClass ->
            if (curClass.id == characterClass.id) characterClass else curClass
        }
    }

    fun getArchetypeById(id: String?): Archetype? {
        return _uiState.value.classes
            .flatMap { it.archetypes }
            .find { it.id == id }
    }

    fun replaceArchetype(
        classes: List<CharacterClass>,
        archetype: Archetype
    ): List<CharacterClass> {
        return classes.map { characterClass ->
            characterClass.copy(
                archetypes = characterClass.archetypes.map { currentArchetype ->
                    if (currentArchetype.id == archetype.id) archetype else currentArchetype
                }
            )
        }
    }

    fun getSubraceById(id: String?): Subrace? {
        return _uiState.value.races
            .flatMap { it.subraces }
            .find { it.id == id }
    }

    fun replaceSubrace(
        races: List<Race>,
        subrace: Subrace
    ): List<Race> {
        return races.map { race ->
            race.copy(
                subraces = race.subraces.map { currentSubrace ->
                    if (currentSubrace.id == subrace.id) subrace else currentSubrace
                }
            )
        }
    }

    fun onSubraceSelected(subrace: Subrace) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                subraceId = subrace.id,
                subraceName = subrace.name
            )
        )

        loadSubraceDetails(subrace.id)
    }

    fun loadSubraceDetails(subraceId: String) {
        val currentSubrace = getSubraceById(subraceId)

        if (currentSubrace != null && currentSubrace.description.isNotBlank()) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                subraceDetailsLoading = true
            )

            runCatching {
                creationRepository.getSubraceDetails(subraceId)
            }.onSuccess { detailedSubrace ->
                _uiState.value = _uiState.value.copy(
                    subraceDetailsLoading = false,
                    races = replaceSubrace(
                        races = _uiState.value.races,
                        subrace = detailedSubrace
                    )
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    subraceDetailsLoading = false
                )

                _events.emit(CreationEvent.ShowError(AppMessage.LoadSubrace))
            }
        }
    }

    fun onClassSelected(characterClass: CharacterClass) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                classId = characterClass.id,
                className = characterClass.name,
                archetypeId = null,
                archetypeName = null
            )
        )

        loadClassDetails(characterClass.id)
    }

    fun onArchetypeSelected(archetype: Archetype) {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                archetypeId = archetype.id,
                archetypeName = archetype.name
            )
        )
        loadArchetypeDetails(archetype.id)
    }

    fun loadArchetypeDetails(archetypeId: String) {
        val currentArchetype = getArchetypeById(archetypeId)

        if (currentArchetype != null && currentArchetype.description.isNotBlank()) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                archetypeDetailsLoading = true
            )

            runCatching {
                creationRepository.getArchetypeDetails(archetypeId)
            }.onSuccess { detailedArchetype ->
                _uiState.value = _uiState.value.copy(
                    archetypeDetailsLoading = false,
                    classes = replaceArchetype(
                        classes = _uiState.value.classes,
                        archetype = detailedArchetype
                    )
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    archetypeDetailsLoading = false
                )

                _events.emit(CreationEvent.ShowError(AppMessage.LoadArchetype))
            }
        }
    }

    fun getRemainingPoints(): Int {
        return PointBuyRules.getRemainingPoints(
            _uiState.value.character.abilityScores
        )
    }

    fun onBackstoryChanged(backstory: String){
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                backstory = backstory
            )
        )
    }

    fun onEquipmentChanged(equipment: String){
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                equipment = equipment
            )
        )
    }

    fun onAdditionalInfoChanged(additionalInfo: String){
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                additionalInfo = additionalInfo
            )
        )
    }

    fun increaseAbility(ability: Ability) {
        val currentScores = _uiState.value.character.abilityScores
        val currentValue = currentScores.get(ability)

        if (currentValue >= MAX_POINT_BUY_SCORE) return

        val nextValue = currentValue + 1
        val currentCost = PointBuyRules.getCost(currentValue) ?: return
        val nextCost = PointBuyRules.getCost(nextValue) ?: return
        val diff = nextCost - currentCost

        if (getRemainingPoints() < diff) return

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

    fun getClassById(id: String?): CharacterClass? =
        _uiState.value.classes.find { it.id == id }

    fun regenerateScores() {
        _uiState.value = _uiState.value.copy(
            character = _uiState.value.character.copy(
                abilityScores = generateScores()
            )
        )
    }

    fun onAbilityGenerationMethodSelected(method: AbilityGenerationMethod) {
        val newScores = when (method) {
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

    fun increaseArmorClass() {
        val character = _uiState.value.character

        if (character.armorClass >= MAX_ARMOR_CLASS) return

        _uiState.value = _uiState.value.copy(
            character = character.copy(
                armorClass = character.armorClass + 1
            )
        )
    }

    fun decreaseArmorClass() {
        val character = _uiState.value.character

        if (character.armorClass <= MIN_ARMOR_CLASS) return

        _uiState.value = _uiState.value.copy(
            character = character.copy(
                armorClass = character.armorClass - 1
            )
        )
    }

    fun increaseMaxHitPoints() {
        val character = _uiState.value.character

        if (character.maxHitPoints >= MAX_HIT_POINTS) return

        val nextMaxHitPoints = character.maxHitPoints + 1

        _uiState.value = _uiState.value.copy(
            character = character.copy(
                maxHitPoints = nextMaxHitPoints,
                currentHitPoints = nextMaxHitPoints
            )
        )
    }

    fun decreaseMaxHitPoints() {
        val character = _uiState.value.character

        if (character.maxHitPoints <= MIN_HIT_POINTS) return

        val nextMaxHitPoints = character.maxHitPoints - 1

        _uiState.value = _uiState.value.copy(
            character = character.copy(
                maxHitPoints = nextMaxHitPoints,
                currentHitPoints = nextMaxHitPoints
            )
        )
    }

    fun increaseLevel() {
        val character = _uiState.value.character

        if (character.level >= MAX_CHARACTER_LEVEL) return

        _uiState.value = _uiState.value.copy(
            character = character.copy(
                level = character.level + 1
            )
        )
    }

    fun decreaseLevel() {
        val character = _uiState.value.character

        if (character.level <= MIN_CHARACTER_LEVEL) return

        _uiState.value = _uiState.value.copy(
            character = character.copy(
                level = character.level - 1
            )
        )
    }

    fun goToNextStep() {
        val state = _uiState.value

        _uiState.value = state.copy(
            currentStep = CreationStepNavigator.getNextStep(
                currentStep = state.currentStep,
                character = state.character
            )
        )
    }

    fun goToPreviousStep() {
        val state = _uiState.value

        _uiState.value = state.copy(
            currentStep = CreationStepNavigator.getPreviousStep(
                currentStep = state.currentStep,
                character = state.character
            )
        )
    }

    fun canGoToNextStep(): Boolean {
        return CreationValidator.canGoToNextStep(_uiState.value)
    }

    fun saveCharacter() {
        val character = _uiState.value.character

        if (!canSaveCharacter()) {
            viewModelScope.launch {
                _events.emit(CreationEvent.ShowError(AppMessage.RequiredFields))
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
                _events.emit(CreationEvent.ShowError(AppMessage.SaveCharacter))
            }
        }
    }

    fun canSaveCharacter(): Boolean {
        return CreationValidator.canSaveCharacter(_uiState.value)
    }
}

sealed interface CreationEvent {
    data object CharacterSaved : CreationEvent
    data class ShowError(val message: AppMessage) : CreationEvent
}