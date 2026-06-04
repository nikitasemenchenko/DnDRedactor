package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.AbilityGenerationMethod
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightButtonColor
import com.example.dndredactor.presentation.theme.LightColor
import com.example.dndredactor.presentation.theme.TextPrimaryDark
import com.example.dndredactor.presentation.theme.TextSecondaryDark

@Composable
fun AbilityGenerationMethodScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val selectedMethod = uiState.character.abilityGenerationMethod

    CreationStepLayout {
        CreationSection(R.string.ability_generation_method) {
            AbilityMethodCard(
                title = stringResource(R.string.random_generation),
                description = stringResource(R.string.random_generation_description),
                icon = Icons.Default.Casino,
                isSelected = selectedMethod == AbilityGenerationMethod.RANDOM,
                onClick = {
                    vm.onAbilityGenerationMethodSelected(AbilityGenerationMethod.RANDOM)
                }
            )

            AbilityMethodCard(
                title = stringResource(R.string.point_buy),
                description = stringResource(R.string.point_buy_description),
                icon = Icons.Default.Tune,
                isSelected = selectedMethod == AbilityGenerationMethod.POINT_BUY,
                onClick = {
                    vm.onAbilityGenerationMethodSelected(AbilityGenerationMethod.POINT_BUY)
                }
            )
        }
    }
}

@Composable
private fun AbilityMethodCard(
    title: String,
    description: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) ButtonColor else LightButtonColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = if (isSelected) Icons.Default.Check else icon,
                contentDescription = null,
                tint = if (isSelected) LightColor else TextPrimaryDark
            )

            Text(
                text = title,
                color = if (isSelected) LightColor else TextPrimaryDark,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = description,
                color = if (isSelected) {
                    LightColor.copy(alpha = 0.82f)
                } else {
                    TextSecondaryDark
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}