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
fun HumanTraitsSelectionScreen(
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
        Title(R.string.appearance_selection)
        CustomTextField(
            value = uiState.character.appearance,
            onChange = vm::onAppearanceChanged,
            labelRes = R.string.appearance_placeholder,
            minLines = 3
        )

        Title(R.string.character_traits)
        CustomTextField(
            value = uiState.character.character,
            onChange = vm::onCharacterChanged,
            labelRes = R.string.character,
            minLines = 2
        )
        CustomTextField(
            value = uiState.character.ideal,
            onChange = vm::onIdealChanged,
            labelRes = R.string.ideal,
            minLines = 2
        )
        CustomTextField(
            value = uiState.character.attachment,
            onChange = vm::onAttachmentChanged,
            labelRes = R.string.attachment,
            minLines = 2
        )
        CustomTextField(
            value = uiState.character.weakness,
            onChange = vm::onWeaknessChanged,
            labelRes = R.string.weakness,
            minLines = 2
        )
    }
}