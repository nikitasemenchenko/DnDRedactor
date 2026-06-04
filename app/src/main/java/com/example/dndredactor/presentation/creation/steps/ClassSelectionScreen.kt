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
fun ClassSelectionScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()

    CreationStepLayout {
        CreationSection(R.string.class_selection) {
            Dropdown(
                items = uiState.classes,
                selectedId = uiState.character.classId,
                idSelector = { it.id },
                nameSelector = { it.name },
                labelRes = R.string.class_selection,
                onSelect = vm::onClassSelected
            )

            if (uiState.classDetailsLoading) {
                CircularProgressIndicator(
                    color = LightColor
                )
            }

            val selectedClass = vm.getClassById(uiState.character.classId)

            if (selectedClass != null && selectedClass.archetypes.isNotEmpty()) {
                Dropdown(
                    items = selectedClass.archetypes,
                    selectedId = uiState.character.archetypeId,
                    idSelector = { it.id },
                    nameSelector = { it.name },
                    labelRes = R.string.archetype_selection,
                    onSelect = vm::onArchetypeSelected
                )

                if (uiState.archetypeDetailsLoading) {
                    CircularProgressIndicator(
                        color = LightColor
                    )
                }

                val selectedArchetype = vm.getArchetypeById(uiState.character.archetypeId)

                DescriptionCard(
                    description = selectedArchetype?.description,
                    placeholderRes = R.string.archetype_desc_placeholder
                )
            }
        }
    }
}