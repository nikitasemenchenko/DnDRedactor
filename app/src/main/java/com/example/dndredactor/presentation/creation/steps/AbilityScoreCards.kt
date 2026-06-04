package com.example.dndredactor.presentation.creation.steps

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.calculateAbilityModifier
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.theme.BackPurple
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun AbilityScoreDisplayRow(
    @StringRes titleRes: Int,
    value: Int
) {
    val modifier = textAsModifier(calculateAbilityModifier(value))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BackPurple.copy(alpha = 0.34f),
                shape = MaterialTheme.shapes.large
            )
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(titleRes),
                color = LightColor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(
                    R.string.ability_score_value,
                    value,
                    modifier
                ),
                color = LightColor.copy(alpha = 0.78f),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Text(
            text = value.toString(),
            color = LightColor,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AbilityScoreControlRow(
    @StringRes titleRes: Int,
    value: Int,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit
) {
    val modifier = textAsModifier(calculateAbilityModifier(value))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BackPurple.copy(alpha = 0.34f),
                shape = MaterialTheme.shapes.large
            )
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(titleRes),
                color = LightColor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(
                    R.string.ability_score_value,
                    value,
                    modifier
                ),
                color = LightColor.copy(alpha = 0.78f),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AbilitySmallButton(
                text = stringResource(R.string.minus),
                onClick = onMinusClick
            )

            Text(
                text = value.toString(),
                color = LightColor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            AbilitySmallButton(
                text = stringResource(R.string.plus),
                onClick = onPlusClick
            )
        }
    }
}

@Composable
private fun AbilitySmallButton(
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