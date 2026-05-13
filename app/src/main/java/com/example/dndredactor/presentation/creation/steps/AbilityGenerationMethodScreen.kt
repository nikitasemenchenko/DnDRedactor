package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dndredactor.data.model.AbilityGenerationMethod
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.LightButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun AbilityGenerationMethodScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val selectedMethod = uiState.character.abilityGenerationMethod

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Способ генерации характеристик",
            color = LightColor,
            style = MaterialTheme.typography.titleLarge
        )

        AbilityGenerationMethod.entries.forEach { method ->
            AbilityMethodCard(
                method = method,
                isSelected = selectedMethod == method,
                onClick = {
                    vm.onAbilityGenerationMethodSelected(method)
                }
            )
        }
    }
}

@Composable
fun AbilityMethodCard(
    method: AbilityGenerationMethod,
    isSelected: Boolean,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if(isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Text(
                text = method.title,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = method.description,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}