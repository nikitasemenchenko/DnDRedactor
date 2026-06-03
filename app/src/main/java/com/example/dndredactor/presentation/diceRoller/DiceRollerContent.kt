package com.example.dndredactor.presentation.diceRoller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.CustomCard
import com.example.dndredactor.presentation.theme.LightColor
import com.example.dndredactor.presentation.theme.TextPrimaryDark
import com.example.dndredactor.presentation.theme.TextSecondaryDark

@Composable
fun DiceRollerContent(
    modifier: Modifier = Modifier,
    state: DiceRollerUiState,
    vm: DiceRollerViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DiceResult(
            state = state
        )

        CustomCard {
            Text(
                text = stringResource(R.string.roll_setup),
                color = TextPrimaryDark,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = formatDiceExpression(
                    diceCount = state.diceCount,
                    diceSides = state.selectedDiceSides,
                    modifier = state.modifier
                ),
                color = TextSecondaryDark,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = stringResource(R.string.select_dice),
                color = TextPrimaryDark,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp)
            )

            FlowRow(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(4, 6, 8, 10, 12, 20, 100).forEach { sides ->
                    DiceTypeButton(
                        text = stringResource(R.string.dice_type, sides),
                        isSelected = state.selectedDiceSides == sides,
                        onClick = {
                            vm.selectDice(sides)
                        }
                    )
                }
            }

            DiceControlRow(
                title = stringResource(R.string.dice_count),
                value = state.diceCount.toString(),
                onMinus = vm::decreaseDiceCount,
                onPlus = vm::increaseDiceCount,
                modifier = Modifier.padding(top = 16.dp)
            )

            DiceControlRow(
                title = stringResource(R.string.modifier),
                value = if (state.modifier >= 0) {
                    stringResource(R.string.modifier_positive, state.modifier)
                } else {
                    stringResource(R.string.modifier_plain, state.modifier)
                },
                onMinus = vm::decreaseModifier,
                onPlus = vm::increaseModifier,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        if (state.history.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.roll_history),
                    color = LightColor,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                TextButton(
                    onClick = vm::clearHistory
                ) {
                    Text(
                        text = stringResource(R.string.clear),
                        color = LightColor
                    )
                }
            }

            state.history.forEach { result ->
                HistoryItem(
                    result = result
                )
            }
        }
    }
}