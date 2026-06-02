package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.CustomTextField
import com.example.dndredactor.presentation.components.Title
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun EquipmentScreen(
    vm: CreationViewModel
){
    val uiState by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(R.string.equipment_selection)
        Text(
            text = stringResource(R.string.equipment_description),
            color = LightColor,
            style = MaterialTheme.typography.bodyLarge
        )
        CustomTextField(
            value = uiState.character.equipment,
            onValueChange = vm::onEquipmentChanged,
            labelRes = R.string.equipment_placeholder,
            minLines = 8
        )
    }
}