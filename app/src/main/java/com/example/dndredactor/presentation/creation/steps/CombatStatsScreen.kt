package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun CombatStatsScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val character = uiState.character

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Title(R.string.combat_stats_selection)

        CombatCounterRow(
            title = "Уровень",
            value = character.level.toString(),
            onMinus = vm::decreaseLevel,
            onPlus = vm::increaseLevel
        )

        Text(
            text = "Бонус мастерства: ${textAsModifier(calculateProficiencyBonus(character.level))}",
            color = LightColor,
            style = MaterialTheme.typography.bodyLarge
        )

        CombatCounterRow(
            title = "Класс доспеха",
            value = character.armorClass.toString(),
            onMinus = vm::decreaseArmorClass,
            onPlus = vm::increaseArmorClass
        )

        CombatCounterRow(
            title = "Максимум HP",
            value = character.maxHitPoints.toString(),
            onMinus = vm::decreaseMaxHitPoints,
            onPlus = vm::increaseMaxHitPoints
        )

        Text(
            text = "Текущие HP при создании будут равны максимуму HP.",
            color = LightColor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun CombatCounterRow(
    title: String,
    value: String,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = LightColor,
            style = MaterialTheme.typography.bodyLarge
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onMinus,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text("-", color = LightColor)
            }

            Text(
                text = value,
                color = LightColor,
                style = MaterialTheme.typography.titleLarge
            )

            Button(
                onClick = onPlus,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text("+", color = LightColor)
            }
        }
    }
}