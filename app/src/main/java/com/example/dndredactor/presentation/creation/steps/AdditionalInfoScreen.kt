package com.example.dndredactor.presentation.creation.steps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.CustomTextField
import com.example.dndredactor.presentation.creation.CreationViewModel

@Composable
fun AdditionalInfoScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()

    CreationStepLayout {
        CreationSection(R.string.notes) {
            CustomTextField(
                value = uiState.character.additionalInfo,
                onValueChange = vm::onAdditionalInfoChanged,
                labelRes = R.string.additional_info_placeholder,
                minLines = 8
            )
        }
    }
}