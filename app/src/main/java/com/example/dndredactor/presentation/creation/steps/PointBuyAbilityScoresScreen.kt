package com.example.dndredactor.presentation.creation.steps

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun PointBuyAbilityScoresScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val scores = uiState.character.abilityScores
    val remainingPoints = vm.getRemainingPoints()

    CreationStepLayout {
        CreationSection(R.string.point_buy) {
            Text(
                text = stringResource(R.string.remaining_points, remainingPoints),
                color = LightColor,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = stringResource(R.string.point_buy_description),
                color = LightColor.copy(alpha = 0.78f),
                style = MaterialTheme.typography.bodyLarge
            )

            Ability.entries.forEach { ability ->
                AbilityScoreControlRow(
                    titleRes = ability.titleRes,
                    value = scores.get(ability),
                    onMinusClick = {
                        vm.decreaseAbility(ability)
                    },
                    onPlusClick = {
                        vm.increaseAbility(ability)
                    }
                )
            }
        }
    }
}