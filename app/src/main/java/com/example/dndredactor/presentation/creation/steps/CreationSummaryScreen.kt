package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.getModifier
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun CreationSummaryScreen(
    vm: CreationViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uiState by vm.uiState.collectAsState()
        val character = uiState.character

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.summary_title),
                color = LightColor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            SummaryBlock(title = stringResource(R.string.main)) {
                SummaryLine(
                    stringResource(R.string.character_name),
                    character.fullName
                )
                SummaryLine(
                    stringResource(R.string.level),
                    character.level.toString()
                )
                SummaryLine(
                    stringResource(R.string.race),
                    character.raceName ?: stringResource(R.string.not_selected)
                )
                SummaryLine(
                    stringResource(R.string.subrace),
                    character.subraceName ?: stringResource(R.string.not_selected)
                )
                SummaryLine(stringResource(R.string.class_name),
                    character.className ?: stringResource(R.string.not_selected)
                )
                SummaryLine(
                    stringResource(R.string.archetype),
                    character.archetypeName ?: stringResource(R.string.not_selected)
                )
                SummaryLine(
                    key = stringResource(R.string.proficiency_bonus),
                    value = textAsModifier(calculateProficiencyBonus(character.level))
                )
            }

            SummaryBlock(title = stringResource(R.string.backstory_selection)) {
                SummaryLine(
                    key = stringResource(R.string.description),
                    value = character.backstory
                )
            }

            SummaryBlock(title =  stringResource(R.string.characteristics)) {
                Ability.entries.forEach { ability ->
                    val score = character.abilityScores.get(ability)
                    val modifier = character.abilityScores.getModifier(ability)

                    SummaryLine(
                        key = stringResource(ability.titleRes),
                        value = "$score (${textAsModifier(modifier)})"
                    )
                }
            }

            SummaryBlock(title = stringResource(R.string.combat_stats_selection)) {
                SummaryLine(
                    key = stringResource(R.string.armor_class),
                    value = character.armorClass.toString()
                )
                SummaryLine(
                    key = stringResource(R.string.hp),
                    value = "${character.currentHitPoints}/${character.maxHitPoints}"
                )
            }

            SummaryBlock(title = stringResource(R.string.equipment_selection)) {
                SummaryLine(
                    key = stringResource(R.string.items),
                    value = character.equipment
                )
            }

            SummaryBlock(title = stringResource(R.string.additional_info_selection)) {
                SummaryLine(
                    key = stringResource(R.string.description),
                    value = character.additionalInfo
                )
            }
        }
    }
}

@Composable
fun SummaryLine(
    key: String,
    value: String
){
    Text(
        text = "$key: ${value.ifBlank { stringResource(R.string.not_specified) } }",
        color = LightColor,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun SummaryBlock(
    title: String,
    content: @Composable () -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            color = LightColor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        content()
    }
}