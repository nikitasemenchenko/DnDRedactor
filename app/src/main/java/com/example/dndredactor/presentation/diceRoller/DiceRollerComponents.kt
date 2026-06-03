package com.example.dndredactor.presentation.diceRoller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.CustomCard
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor
import com.example.dndredactor.presentation.theme.SurfacePurpleLight
import com.example.dndredactor.presentation.theme.TextPrimaryDark
import com.example.dndredactor.presentation.theme.TextSecondaryDark
import kotlin.math.abs

@Composable
fun DiceResult(
    state: DiceRollerUiState
) {
    val result = state.lastResult

    CustomCard {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = SurfacePurpleLight.copy(alpha = 0.16f),
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Casino,
                        contentDescription = null,
                        tint = TextPrimaryDark,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = result?.let {
                            formatDiceExpression(
                                diceCount = it.diceCount,
                                diceSides = it.diceSides,
                                modifier = it.modifier
                            )
                        } ?: formatDiceExpression(
                            diceCount = state.diceCount,
                            diceSides = state.selectedDiceSides,
                            modifier = state.modifier
                        ),
                        color = TextPrimaryDark,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = stringResource(R.string.latest_result),
                color = TextSecondaryDark,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = result?.total?.toString()
                    ?: stringResource(R.string.no_roll_yet),
                color = TextPrimaryDark,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            if (result != null) {
                Text(
                    text = stringResource(
                        R.string.rolls,
                        result.rolls.joinToString()
                    ),
                    color = TextSecondaryDark,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun DiceTypeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.heightIn(min = 44.dp),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) ButtonColor else SurfacePurpleLight.copy(alpha = 0.18f),
            contentColor = if (isSelected) LightColor else TextPrimaryDark
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DiceControlRow(
    title: String,
    value: String,
    onMinus: () -> Unit,
    onPlus: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SurfacePurpleLight.copy(alpha = 0.12f),
                shape = MaterialTheme.shapes.large
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                color = TextPrimaryDark,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = value,
                color = TextSecondaryDark,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DiceSmallButton(
                text = stringResource(R.string.minus),
                onClick = onMinus
            )

            Text(
                text = value,
                color = TextPrimaryDark,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            DiceSmallButton(
                text = stringResource(R.string.plus),
                onClick = onPlus
            )
        }
    }
}

@Composable
private fun DiceSmallButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(42.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor,
            contentColor = LightColor
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HistoryItem(
    result: DiceRollResult
) {
    CustomCard(
        contentPadding = PaddingValues(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                tint = TextSecondaryDark,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(
                        R.string.dice_history_expression,
                        formatDiceExpression(
                            diceCount = result.diceCount,
                            diceSides = result.diceSides,
                            modifier = result.modifier
                        ),
                        result.total
                    ),
                    color = TextPrimaryDark,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = stringResource(
                        R.string.rolls,
                        result.rolls.joinToString()
                    ),
                    color = TextSecondaryDark,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun formatDiceExpression(
    diceCount: Int,
    diceSides: Int,
    modifier: Int
): String {
    val modifierText = when {
        modifier > 0 -> {
            stringResource(R.string.dice_modifier_positive, modifier)
        }

        modifier < 0 -> {
            stringResource(R.string.dice_modifier_negative, abs(modifier))
        }

        else -> {
            ""
        }
    }

    return stringResource(
        R.string.dice_expression,
        diceCount,
        diceSides,
        modifierText
    )
}