package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.calculateAbilityModifier
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.components.CounterRow
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.LightButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun PointBuyAbilityScoresScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val scores = uiState.character.abilityScores
    val remainingPoints = vm.getRemainingPoints()

    CreationStepLayout(
        titleRes = R.string.point_buy
    ) {
        Text(
            text = stringResource(R.string.remaining_points, remainingPoints),
            color = LightColor,
            style = MaterialTheme.typography.titleMedium
        )

        Ability.entries.forEach { ability ->
            PointBuyAbilityCard(
                title = stringResource(ability.titleRes),
                value = scores.get(ability),
                onMinusClick = { vm.decreaseAbility(ability) },
                onPlusClick = { vm.increaseAbility(ability) }
            )
        }
    }
}

@Composable
fun PointBuyAbilityCard(
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
        CounterRow(
            title = title,
            value = "$value (${textAsModifier(calculateAbilityModifier(value))})",
            onPlus = onPlusClick,
            onMinus = onMinusClick
        )
    }
}