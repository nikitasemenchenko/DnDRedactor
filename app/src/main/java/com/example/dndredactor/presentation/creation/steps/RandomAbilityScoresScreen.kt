package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun RandomAbilityScoresScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val scores = uiState.character.abilityScores

    CreationStepLayout(
        titleRes = R.string.random_abilities
    ) {
        Button(
            onClick = vm::regenerateScores,
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.regenerate),
                color = LightColor,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Ability.entries.forEach { ability ->
            AbilityScoreDisplayCard(
                titleRes = ability.titleRes,
                value = scores.get(ability)
            )
        }
    }
}