package com.example.dndredactor.presentation.creation

import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.CharacterDraft
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.presentation.components.AppMessage

data class CreationUiState(
    val currentStep: CreationStep = CreationStep.RACE,
    val character: CharacterDraft = CharacterDraft(),
    val races: List<Race> = emptyList(),
    val classes: List<CharacterClass> = emptyList(),
    val raceDetailsLoading: Boolean = false,
    val subraceDetailsLoading: Boolean = false,
    val classDetailsLoading: Boolean = false,
    val archetypeDetailsLoading: Boolean = false,
    val loading: Boolean = false,
    val error: AppMessage? = null
)

enum class CreationStep {
    RACE,
    CLASS,
    BACKSTORY,
    TRAITS,
    ABILITY_GENERATION_METHOD,
    RANDOM_ABILITIES,
    POINT_BUY_ABILITIES,
    COMBAT_STATS,
    EQUIPMENT,
    ADDITIONAL_INFO,
    FINAL
}
