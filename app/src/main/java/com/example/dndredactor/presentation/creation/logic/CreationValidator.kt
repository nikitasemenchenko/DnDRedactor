package com.example.dndredactor.presentation.creation.logic

import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.presentation.creation.CreationStep
import com.example.dndredactor.presentation.creation.CreationUiState

object CreationValidator {

    fun canGoToNextStep(state: CreationUiState): Boolean {
        val character = state.character
        val selectedRace = state.races.find { it.id == character.raceId }
        val selectedClass = state.classes.find { it.id == character.classId }

        return when (state.currentStep) {
            CreationStep.RACE -> {
                character.fullName.isNotBlank() &&
                        character.gender != Gender.UNSPECIFIED &&
                        selectedRace != null &&
                        !state.raceDetailsLoading &&
                        !state.subraceDetailsLoading &&
                        (
                                selectedRace.subraces.isEmpty() ||
                                        character.subraceId != null
                                )
            }

            CreationStep.CLASS -> {
                selectedClass != null &&
                        !state.classDetailsLoading &&
                        !state.archetypeDetailsLoading &&
                        (
                                selectedClass.archetypes.isEmpty() ||
                                        character.archetypeId != null
                                )
            }

            CreationStep.BACKSTORY -> true

            CreationStep.TRAITS -> true

            CreationStep.ABILITY_GENERATION_METHOD -> {
                character.abilityGenerationMethod != null
            }

            CreationStep.RANDOM_ABILITIES -> true

            CreationStep.POINT_BUY_ABILITIES -> {
                PointBuyRules.getRemainingPoints(character.abilityScores) >= 0
            }

            CreationStep.COMBAT_STATS -> {
                character.armorClass > 0 &&
                        character.maxHitPoints > 0 &&
                        character.currentHitPoints in 0..character.maxHitPoints
            }

            CreationStep.EQUIPMENT -> true

            CreationStep.ADDITIONAL_INFO -> true

            CreationStep.FINAL -> true
        }
    }

    fun canSaveCharacter(state: CreationUiState): Boolean {
        val character = state.character
        val selectedRace = state.races.find { it.id == character.raceId }
        val selectedClass = state.classes.find { it.id == character.classId }

        return character.fullName.isNotBlank() &&
                character.gender != Gender.UNSPECIFIED &&
                character.level in CreationLimits.MIN_CHARACTER_LEVEL..CreationLimits.MAX_CHARACTER_LEVEL &&
                selectedRace != null &&
                selectedClass != null &&
                !state.raceDetailsLoading &&
                !state.subraceDetailsLoading &&
                !state.classDetailsLoading &&
                !state.archetypeDetailsLoading &&
                character.armorClass > 0 &&
                character.maxHitPoints > 0 &&
                character.currentHitPoints in 0..character.maxHitPoints &&
                (
                        selectedRace.subraces.isEmpty() ||
                                character.subraceId != null
                        ) &&
                (
                        selectedClass.archetypes.isEmpty() ||
                                character.archetypeId != null
                        )
    }
}