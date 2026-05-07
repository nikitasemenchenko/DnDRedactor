package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.presentation.creation.CreationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceSelectionScreen(
    vm: CreationViewModel = viewModel(),
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
        CustomTextField(
            value = uiState.character.fullName,
            onChange = vm::onNameChanged,
            labelRes = R.string.character_name
        )

        Title(R.string.character_gender)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GenderButton(
                text = stringResource(R.string.male),
                isSelected = uiState.character.gender is Gender.MALE,
                onClick = { vm.onGenderSelected(Gender.MALE) }
            )
            GenderButton(
                text = stringResource(R.string.female),
                isSelected = uiState.character.gender is Gender.FEMALE,
                onClick = { vm.onGenderSelected(Gender.FEMALE) }
            )
        }

        Title(R.string.race_selection)
        Dropdown(
            items = uiState.races,
            selectedId = uiState.character.raceId,
            onSelect = vm::onRaceSelected,
            idSelector = { it.id },
            nameSelector = { it.name },
            labelRes = R.string.character_race
        )
        DescriptionCard(
            desc = vm.getRaceById(uiState.character.raceId)?.description,
            placeholder = R.string.race_desc_placeholder
        )

        val selectedRace = vm.getRaceById(uiState.character.raceId)
        if (selectedRace != null && selectedRace.subraces.isNotEmpty()) {
            val selectedSubrace = vm.getSubraceById(selectedRace, uiState.character.subraceId)
            Title(R.string.subrace_selection)
            Dropdown(
                items = selectedRace.subraces,
                selectedId = uiState.character.subraceId,
                onSelect = vm::onSubraceSelected,
                idSelector = { it.id },
                nameSelector = { it.name },
                labelRes = R.string.character_subrace
            )
            DescriptionCard(
                desc = selectedSubrace?.description,
                placeholder = R.string.subrace_desc_placeholder
            )
        }
    }
}