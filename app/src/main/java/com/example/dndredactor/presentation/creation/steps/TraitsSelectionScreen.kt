package com.example.dndredactor.presentation.creation.steps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.CustomTextField
import com.example.dndredactor.presentation.components.Title
import com.example.dndredactor.presentation.creation.CreationViewModel

@Composable
fun TraitsSelectionScreen(
    vm: CreationViewModel,
) {

    val uiState by vm.uiState.collectAsState()

    CreationStepLayout(
        titleRes = R.string.appearance_traits
    ) {
        Title(R.string.appearance_selection)

        CustomTextField(
            value = uiState.character.appearance,
            onValueChange = vm::onAppearanceChanged,
            labelRes = R.string.appearance_placeholder,
            minLines = 3
        )

        Title(R.string.character_traits)

        CustomTextField(
            value = uiState.character.personality,
            onValueChange = vm::onPersonalityChanged,
            labelRes = R.string.personality,
            minLines = 2
        )

        CustomTextField(
            value = uiState.character.ideal,
            onValueChange = vm::onIdealChanged,
            labelRes = R.string.ideal,
            minLines = 2
        )

        CustomTextField(
            value = uiState.character.attachment,
            onValueChange = vm::onAttachmentChanged,
            labelRes = R.string.attachment,
            minLines = 2
        )

        CustomTextField(
            value = uiState.character.weakness,
            onValueChange = vm::onWeaknessChanged,
            labelRes = R.string.weakness,
            minLines = 2
        )
    }
}