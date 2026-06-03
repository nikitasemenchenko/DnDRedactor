package com.example.dndredactor.presentation.creation.steps

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.DescriptionCard
import com.example.dndredactor.presentation.components.Dropdown
import com.example.dndredactor.presentation.components.Title
import com.example.dndredactor.presentation.creation.CreationViewModel

@Composable
fun ClassSelectionScreen(
    vm: CreationViewModel,
) {
    val uiState by vm.uiState.collectAsState()

    CreationStepLayout(
        titleRes = R.string.class_selection
    ) {
        Dropdown(
            items = uiState.classes,
            selectedId = uiState.character.classId,
            idSelector = { it.id },
            nameSelector = { it.name },
            labelRes = R.string.class_selection,
            onSelect = vm::onClassSelected
        )

        if (uiState.classDetailsLoading) {
            CircularProgressIndicator()
        }

        val selectedClass = vm.getClassById(uiState.character.classId)
        if (selectedClass != null && selectedClass.archetypes.isNotEmpty()) {
            Title(R.string.archetype_selection)
            Dropdown(
                items = selectedClass.archetypes,
                selectedId = uiState.character.archetypeId,
                idSelector = { it.id },
                nameSelector = { it.name },
                labelRes = R.string.archetype_selection,
                onSelect = vm::onArchetypeSelected
            )

            if (uiState.archetypeDetailsLoading) {
                CircularProgressIndicator()
            }

            val selectedArchetype = vm.getArchetypeById(uiState.character.archetypeId)

            DescriptionCard(
                description = selectedArchetype?.description,
                placeholderRes = R.string.archetype_desc_placeholder
            )
        }
    }
}