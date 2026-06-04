package com.example.dndredactor.presentation.creation.steps

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.DescriptionCard
import com.example.dndredactor.presentation.components.Dropdown
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun RaceSelectionScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()

    CreationStepLayout {
        CreationSection(R.string.race_selection) {
            Dropdown(
                items = uiState.races,
                selectedId = uiState.character.raceId,
                onSelect = vm::onRaceSelected,
                idSelector = { it.id },
                nameSelector = { it.name },
                labelRes = R.string.character_race
            )

            DescriptionCard(
                description = vm.getRaceById(uiState.character.raceId)?.description,
                placeholderRes = R.string.race_desc_placeholder
            )

            if (uiState.raceDetailsLoading) {
                CircularProgressIndicator(
                    color = LightColor
                )
            }

            val selectedRace = vm.getRaceById(uiState.character.raceId)

            if (selectedRace != null && selectedRace.subraces.isNotEmpty()) {
                Dropdown(
                    items = selectedRace.subraces,
                    selectedId = uiState.character.subraceId,
                    onSelect = vm::onSubraceSelected,
                    idSelector = { it.id },
                    nameSelector = { it.name },
                    labelRes = R.string.character_subrace
                )

                if (uiState.subraceDetailsLoading) {
                    CircularProgressIndicator(
                        color = LightColor
                    )
                }

                val selectedSubrace = vm.getSubraceById(uiState.character.subraceId)

                DescriptionCard(
                    description = selectedSubrace?.description,
                    placeholderRes = R.string.subrace_desc_placeholder
                )
            }
        }
    }
}