package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Gender
import com.example.dndredactor.presentation.components.CustomTextField
import com.example.dndredactor.presentation.components.SelectableButton
import com.example.dndredactor.presentation.creation.CreationViewModel

@Composable
fun CharacterIdentityScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()

    CreationStepLayout {
        CreationSection(R.string.main) {
            CustomTextField(
                value = uiState.character.fullName,
                onValueChange = vm::onNameChanged,
                labelRes = R.string.character_name
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SelectableButton(
                    text = stringResource(R.string.male),
                    isSelected = uiState.character.gender == Gender.MALE,
                    onClick = { vm.onGenderSelected(Gender.MALE) }
                )

                SelectableButton(
                    text = stringResource(R.string.female),
                    isSelected = uiState.character.gender == Gender.FEMALE,
                    onClick = { vm.onGenderSelected(Gender.FEMALE) }
                )
            }
        }

        CreationSection(R.string.appearance_selection) {
            CustomTextField(
                value = uiState.character.appearance,
                onValueChange = vm::onAppearanceChanged,
                labelRes = R.string.appearance_placeholder,
                minLines = 5
            )
        }
    }
}