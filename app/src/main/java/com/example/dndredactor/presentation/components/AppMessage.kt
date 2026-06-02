package com.example.dndredactor.presentation.components

import androidx.annotation.StringRes
import com.example.dndredactor.R

enum class AppMessage(
    @StringRes val resId: Int
) {
    Unknown(R.string.error_unknown),

    RequiredFields(R.string.error_required_fields),
    LoadCharacters(R.string.error_load_characters),
    LoadCharacter(R.string.error_load_character),
    CharacterNotFound(R.string.error_character_not_found),

    LoadReferenceData(R.string.error_load_reference_data),
    LoadRace(R.string.error_load_race),
    LoadSubrace(R.string.error_load_subrace),
    LoadClass(R.string.error_load_class),
    LoadArchetype(R.string.error_load_archetype),

    SaveCharacter(R.string.error_save_character),
    UpdateCharacter(R.string.error_update_character),

    WaitForLoading(R.string.error_wait_for_loading),
    SelectSubrace(R.string.error_select_subrace),
    SelectArchetype(R.string.error_select_archetype),
    InvalidCharacter(R.string.error_invalid_character),
    InvalidCombatStats(R.string.error_invalid_combat_stats)
}