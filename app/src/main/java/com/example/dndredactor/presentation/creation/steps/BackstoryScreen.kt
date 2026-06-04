package com.example.dndredactor.presentation.creation.steps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.CustomTextField
import com.example.dndredactor.presentation.creation.CreationViewModel

@Composable
fun BackstoryScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()

    CreationStepLayout(
        titleRes = R.string.backstory_selection,
        descriptionRes = R.string.backstory_description
    ) {
        CreationSection(R.string.description) {
            CustomTextField(
                value = uiState.character.backstory,
                onValueChange = vm::onBackstoryChanged,
                labelRes = R.string.backstory_placeholder,
                minLines = 8
            )
        }
    }
}