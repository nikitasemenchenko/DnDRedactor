package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dndredactor.R
import com.example.dndredactor.presentation.AppViewModelProvider
import com.example.dndredactor.presentation.creation.CreationViewModel

@Composable
fun ClassSelectionScreen(
    vm: CreationViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by vm.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(R.string.class_selection)
        Dropdown(
            items = uiState.classes,
            selectedId = uiState.character.classId,
            idSelector = { it.id },
            nameSelector = { it.name },
            labelRes = R.string.class_selection,
            onSelect = vm::onClassSelected
        )
        DescriptionCard(
            desc = vm.getClassById(uiState.character.classId)?.description,
            placeholder = R.string.class_desc_placeholder
        )

        val selectedClass = vm.getClassById(uiState.character.classId)
        if (selectedClass != null) {
            Title(R.string.archetype_selection)
            Dropdown(
                items = selectedClass.archetypes,
                selectedId = uiState.character.archetypeId,
                idSelector = { it.id },
                nameSelector = { it.name },
                labelRes = R.string.archetype_selection,
                onSelect = vm::onArchetypeSelected
            )
            DescriptionCard(
                desc = vm.getArchetypeById(
                    selectedClass,
                    uiState.character.archetypeId
                )?.description,
                placeholder = R.string.class_desc_placeholder
            )
        }
    }
}