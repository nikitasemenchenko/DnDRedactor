package com.example.dndredactor.presentation.characterEdit.logic

import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.presentation.characterEdit.CharacterEditUiState
import com.example.dndredactor.presentation.components.AppMessage

object CharacterEditValidator {

    fun validateBeforeSave(
        state: CharacterEditUiState.Success
    ): AppMessage? {
        val character = state.character

        if (
            state.coreLoading ||
            state.raceDetailsLoading ||
            state.classDetailsLoading
        ) {
            return AppMessage.WaitForLoading
        }

        val selectedRace = state.races.find { it.id == character.raceId }
        val selectedClass = state.classes.find { it.id == character.classId }

        if (
            selectedRace != null &&
            selectedRace.subraces.isNotEmpty() &&
            character.subraceId == null
        ) {
            return AppMessage.SelectSubrace
        }

        if (
            selectedClass != null &&
            selectedClass.archetypes.isNotEmpty() &&
            character.archetypeId == null
        ) {
            return AppMessage.SelectArchetype
        }

        if (
            character.name.isBlank() ||
            character.gender == Gender.UNSPECIFIED ||
            character.level !in MIN_CHARACTER_LEVEL..MAX_CHARACTER_LEVEL
        ) {
            return AppMessage.InvalidCharacter
        }

        if (
            character.armorClass <= 0 ||
            character.maxHitPoints <= 0 ||
            character.currentHitPoints !in 0..character.maxHitPoints
        ) {
            return AppMessage.InvalidCombatStats
        }

        return null
    }

    const val MIN_CHARACTER_LEVEL = 1
    const val MAX_CHARACTER_LEVEL = 20

    const val MIN_ARMOR_CLASS = 1
    const val MAX_ARMOR_CLASS = 40

    const val MIN_HIT_POINTS = 1
    const val MAX_HIT_POINTS = 1000
}