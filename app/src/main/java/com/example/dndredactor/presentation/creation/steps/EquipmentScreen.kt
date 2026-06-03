package com.example.dndredactor.presentation.creation.steps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.CustomTextField
import com.example.dndredactor.presentation.creation.CreationViewModel

@Composable
fun EquipmentScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()

    CreationStepLayout(
        titleRes = R.string.equipment_selection,
        descriptionRes = R.string.equipment_description
    ) {
        CustomTextField(
            value = uiState.character.equipment,
            onValueChange = vm::onEquipmentChanged,
            labelRes = R.string.equipment_placeholder,
            minLines = 8
        )
    }
}