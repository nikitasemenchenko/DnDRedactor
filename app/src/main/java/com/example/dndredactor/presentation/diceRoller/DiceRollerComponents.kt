package com.example.dndredactor.presentation.diceRoller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun DiceTypeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LightButtonColor else ButtonColor,
            contentColor = if (isSelected) Color.Black else LightColor
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(text)
    }
}

@Composable
fun DiceCounterCard(
    title: String,
    value: String,
    onMinus: () -> Unit,
    onPlus: () -> Unit
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
                .padding(16.dp),
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
                    onClick = onMinus,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor
                    )
                ) {
                    Text(
                        text = "-",
                        color = LightColor
                    )
                }

                Text(
                    text = value,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge
                )

                Button(
                    onClick = onPlus,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor
                    )
                ) {
                    Text(
                        text = "+",
                        color = LightColor
                    )
                }
            }
        }
    }
}

@Composable
fun LastResultCard(
    result: DiceRollResult?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.result),
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            if (result == null) {
                Text(
                    text = stringResource(R.string.roll_to_start),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                Text(
                    text = result.getResults(),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = result.total.toString(),
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(
                        R.string.rolls,
                        result.rolls.joinToString()
                    ),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun HistoryItem(
    result: DiceRollResult
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "${result.getResults()} = ${result.total}",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(
                    R.string.rolls,
                    result.rolls.joinToString()
                ),
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}