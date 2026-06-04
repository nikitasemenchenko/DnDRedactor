package com.example.dndredactor.presentation.creation.steps

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.presentation.components.CustomCard
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.TextPrimaryDark
import com.example.dndredactor.presentation.theme.TextSecondaryDark

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
        CustomCard {
            Text(
                text = stringResource(R.string.remaining_points, remainingPoints),
                color = TextPrimaryDark,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = stringResource(R.string.point_buy_description),
                color = TextSecondaryDark,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Ability.entries.forEach { ability ->
            AbilityScoreControlCard(
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