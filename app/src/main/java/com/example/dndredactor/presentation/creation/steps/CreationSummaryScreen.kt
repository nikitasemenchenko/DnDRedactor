package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.getModifier
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.creation.CreationViewModel

@Composable
fun CreationSummaryScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val character = uiState.character

    CreationStepLayout(
        titleRes = R.string.summary_title
    ) {
        SummaryCard(titleRes = R.string.main) {
            SummaryRow(
                labelRes = R.string.character_name,
                value = character.fullName
            )

            SummaryRow(
                labelRes = R.string.level,
                value = character.level.toString()
            )

            SummaryRow(
                labelRes = R.string.character_gender,
                value = stringResource(character.gender.titleRes)
            )

            SummaryRow(
                labelRes = R.string.race,
                value = character.raceName ?: stringResource(R.string.not_selected)
            )

            SummaryRow(
                labelRes = R.string.subrace,
                value = character.subraceName ?: stringResource(R.string.not_selected)
            )

            SummaryRow(
                labelRes = R.string.class_name,
                value = character.className ?: stringResource(R.string.not_selected)
            )

            SummaryRow(
                labelRes = R.string.archetype,
                value = character.archetypeName ?: stringResource(R.string.not_selected)
            )

            SummaryRow(
                labelRes = R.string.proficiency_bonus,
                value = textAsModifier(calculateProficiencyBonus(character.level))
            )
        }

        SummaryCard(titleRes = R.string.characteristics) {
            Ability.entries.forEach { ability ->
                val score = character.abilityScores.get(ability)
                val modifier = character.abilityScores.getModifier(ability)

                SummaryRow(
                    labelRes = ability.titleRes,
                    value = stringResource(
                        R.string.ability_score_value,
                        score,
                        textAsModifier(modifier)
                    )
                )
            }
        }

        SummaryCard(titleRes = R.string.combat_stats_selection) {
            SummaryRow(
                labelRes = R.string.armor_class,
                value = character.armorClass.toString()
            )

            SummaryRow(
                labelRes = R.string.hp,
                value = stringResource(
                    R.string.hit_points_value,
                    character.currentHitPoints,
                    character.maxHitPoints
                )
            )
        }

        SummaryCard(titleRes = R.string.backstory_selection) {
            SummaryTextSection(
                titleRes = R.string.description,
                text = character.backstory
            )
        }

        SummaryCard(titleRes = R.string.equipment_selection) {
            SummaryTextSection(
                titleRes = R.string.items,
                text = character.equipment
            )
        }

        SummaryCard(titleRes = R.string.appearance_traits) {
            SummaryTextSection(
                titleRes = R.string.appearance_selection,
                text = character.appearance
            )

            Spacer(modifier = Modifier.height(8.dp))

            SummaryTextSection(
                titleRes = R.string.personality,
                text = character.personality
            )

            Spacer(modifier = Modifier.height(8.dp))

            SummaryTextSection(
                titleRes = R.string.ideal,
                text = character.ideal
            )

            Spacer(modifier = Modifier.height(8.dp))

            SummaryTextSection(
                titleRes = R.string.attachment,
                text = character.attachment
            )

            Spacer(modifier = Modifier.height(8.dp))

            SummaryTextSection(
                titleRes = R.string.weakness,
                text = character.weakness
            )
        }

        SummaryCard(titleRes = R.string.additional_info_selection) {
            SummaryTextSection(
                titleRes = R.string.description,
                text = character.additionalInfo
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}