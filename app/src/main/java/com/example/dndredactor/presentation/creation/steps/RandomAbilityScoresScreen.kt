package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightButtonColor
import com.example.dndredactor.presentation.theme.LightColor
import kotlin.math.floor

@Composable
fun RandomAbilityScoresScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val scores = uiState.character.abilityScores

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Случайные характеристики",
            color = LightColor,
            style = MaterialTheme.typography.titleLarge
        )

        Button(
            onClick = vm::regenerateScores,
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Сгенерировать заново",
                color = LightColor
            )
        }

        Ability.entries.forEach { ability ->
            AbilityResultCard(
                title = ability.title,
                value = scores.get(ability)
            )
        }
    }
}

@Composable
private fun AbilityResultCard(
    title: String,
    value: Int
) {
    val modifierValue = floor((value - 10) / 2.0).toInt()
    val modifierText = if (modifierValue >= 0) "+$modifierValue" else modifierValue.toString()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "$value ($modifierText)",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}