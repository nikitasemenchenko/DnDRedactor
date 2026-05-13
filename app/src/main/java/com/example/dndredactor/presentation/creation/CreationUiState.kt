package com.example.dndredactor.presentation.creation

import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.CharacterDraft
import com.example.dndredactor.data.model.Race

data class CreationUiState(
    val currentStep: CreationStep = CreationStep.RACE,
    val character: CharacterDraft = CharacterDraft(),
    val races: List<Race> = emptyList(),
    val classes: List<CharacterClass> = emptyList(),
    val loading: Boolean = false,
    val error: Int? = null
)

enum class CreationStep {
    RACE,
    CLASS,
    HUMAN_TRAITS,
    ABILITY_GENERATION_METHOD,
    RANDOM_ABILITIES,
    POINT_BUY_ABILITIES,
    FINAL
}
