package com.example.dndredactor.presentation.characterEdit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.Dropdown
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun CharacterEditCoreBlock(
    state: CharacterEditUiState.Success,
    vm: CharacterEditViewModel
) {
    val character = state.character

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.character_main_characteristics),
            color = LightColor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        if (state.coreLoading) {
            CircularProgressIndicator()
            return@Column
        }

        Dropdown(
            items = state.races,
            selectedId = character.raceId,
            idSelector = { it.id },
            nameSelector = { it.name },
            labelRes = R.string.character_race,
            onSelect = vm::onRaceSelected
        )

        if (state.raceDetailsLoading) {
            CircularProgressIndicator()
        }

        val selectedRace = state.races.find { it.id == character.raceId }

        if (selectedRace != null && selectedRace.subraces.isNotEmpty()) {
            Dropdown(
                items = selectedRace.subraces,
                selectedId = character.subraceId,
                idSelector = { it.id },
                nameSelector = { it.name },
                labelRes = R.string.character_subrace,
                onSelect = vm::onSubraceSelected
            )
        }

        Dropdown(
            items = state.classes,
            selectedId = character.classId,
            idSelector = { it.id },
            nameSelector = { it.name },
            labelRes = R.string.class_selection,
            onSelect = vm::onClassSelected
        )

        if (state.classDetailsLoading) {
            CircularProgressIndicator()
        }

        val selectedClass = state.classes.find { it.id == character.classId }

        if (selectedClass != null && selectedClass.archetypes.isNotEmpty()) {
            Dropdown(
                items = selectedClass.archetypes,
                selectedId = character.archetypeId,
                idSelector = { it.id },
                nameSelector = { it.name },
                labelRes = R.string.archetype_selection,
                onSelect = vm::onArchetypeSelected
            )
        }
    }
}