package com.example.dndredactor.presentation.creation.steps

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.dndredactor.R
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.components.CounterRow
import com.example.dndredactor.presentation.components.ReadOnlyInfo
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun CombatStatsScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val character = uiState.character

    CreationStepLayout {
        CreationSection(R.string.combat_stats_selection) {
            CounterRow(
                title = stringResource(R.string.level),
                value = character.level.toString(),
                onMinus = vm::decreaseLevel,
                onPlus = vm::increaseLevel
            )

            ReadOnlyInfo(
                title = stringResource(R.string.proficiency_bonus),
                value = textAsModifier(calculateProficiencyBonus(character.level))
            )

            CounterRow(
                title = stringResource(R.string.armor_class),
                value = character.armorClass.toString(),
                onMinus = vm::decreaseArmorClass,
                onPlus = vm::increaseArmorClass
            )

            CounterRow(
                title = stringResource(R.string.max_hit_points),
                value = character.maxHitPoints.toString(),
                onMinus = vm::decreaseMaxHitPoints,
                onPlus = vm::increaseMaxHitPoints
            )

            Text(
                text = stringResource(R.string.creation_hp_hint),
                color = LightColor.copy(alpha = 0.78f),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}