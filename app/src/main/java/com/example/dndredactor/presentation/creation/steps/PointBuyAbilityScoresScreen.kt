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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun PointBuyAbilityScoresScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val scores = uiState.character.abilityScores
    val remainingPoints = vm.getRemainingPoints()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Закуп характеристик",
            color = LightColor,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Осталось очков: $remainingPoints",
            color = LightColor,
            style = MaterialTheme.typography.titleMedium
        )

        Ability.entries.forEach { ability ->
            PointBuyAbilityCard(
                title = ability.title,
                value = scores.get(ability),
                onMinusClick = { vm.decreaseAbility(ability) },
                onPlusClick = { vm.increaseAbility(ability) }
            )
        }
    }
}

@Composable
private fun PointBuyAbilityCard(
    title: String,
    value: Int,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit
) {
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onMinusClick,
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                ) {
                    Text("-", color = LightColor)
                }

                Text(
                    text = value.toString(),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge
                )

                Button(
                    onClick = onPlusClick,
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                ) {
                    Text("+", color = LightColor)
                }
            }
        }
    }
}