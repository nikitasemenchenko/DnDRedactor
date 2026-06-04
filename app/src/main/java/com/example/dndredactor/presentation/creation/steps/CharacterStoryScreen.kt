package com.example.dndredactor.presentation.creation.steps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.CustomTextField
import com.example.dndredactor.presentation.creation.CreationViewModel

@Composable
fun CharacterStoryScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()

    CreationStepLayout {
        CreationSection(R.string.character_traits) {
            CustomTextField(
                value = uiState.character.personality,
                onValueChange = vm::onPersonalityChanged,
                labelRes = R.string.personality,
                minLines = 3
            )

            CustomTextField(
                value = uiState.character.ideal,
                onValueChange = vm::onIdealChanged,
                labelRes = R.string.ideal,
                minLines = 3
            )

            CustomTextField(
                value = uiState.character.attachment,
                onValueChange = vm::onAttachmentChanged,
                labelRes = R.string.attachment,
                minLines = 3
            )

            CustomTextField(
                value = uiState.character.weakness,
                onValueChange = vm::onWeaknessChanged,
                labelRes = R.string.weakness,
                minLines = 3
            )
        }

        CreationSection(R.string.backstory_selection) {
            CustomTextField(
                value = uiState.character.backstory,
                onValueChange = vm::onBackstoryChanged,
                labelRes = R.string.backstory_placeholder,
                minLines = 8
            )
        }
    }
}