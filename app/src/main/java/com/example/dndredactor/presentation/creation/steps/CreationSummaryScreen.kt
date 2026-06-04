package com.example.dndredactor.presentation.creation.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.ClassType
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.getModifier
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.components.CustomCard
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.mappers.toClassIcon
import com.example.dndredactor.presentation.theme.TextPrimaryDark
import com.example.dndredactor.presentation.theme.TextSecondaryDark

@Composable
fun CreationSummaryScreen(
    vm: CreationViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val character = uiState.character

    CreationStepLayout {
        SummaryHero(
            name = character.fullName,
            level = character.level,
            raceName = character.raceName,
            className = character.className,
            classType = ClassType.fromApiId(character.classId),
            armorClass = character.armorClass,
            currentHitPoints = character.currentHitPoints,
            maxHitPoints = character.maxHitPoints
        )

        CreationSection(R.string.main) {
            SummaryInfoRow(
                labelRes = R.string.character_name,
                value = character.fullName
            )

            SummaryInfoRow(
                labelRes = R.string.character_gender,
                value = stringResource(character.gender.titleRes)
            )

            SummaryInfoRow(
                labelRes = R.string.race,
                value = character.raceName ?: stringResource(R.string.not_selected)
            )

            SummaryInfoRow(
                labelRes = R.string.subrace,
                value = character.subraceName ?: stringResource(R.string.not_selected)
            )

            SummaryInfoRow(
                labelRes = R.string.class_name,
                value = character.className ?: stringResource(R.string.not_selected)
            )

            SummaryInfoRow(
                labelRes = R.string.archetype,
                value = character.archetypeName ?: stringResource(R.string.not_selected)
            )
        }

        CreationSection(R.string.characteristics) {
            Ability.entries.forEach { ability ->
                val score = character.abilityScores.get(ability)
                val modifier = character.abilityScores.getModifier(ability)

                SummaryInfoRow(
                    labelRes = ability.titleRes,
                    value = stringResource(
                        R.string.ability_score_value,
                        score,
                        textAsModifier(modifier)
                    )
                )
            }
        }

        CreationSection(R.string.combat_stats_selection) {
            SummaryInfoRow(
                labelRes = R.string.level,
                value = character.level.toString()
            )

            SummaryInfoRow(
                labelRes = R.string.proficiency_bonus,
                value = textAsModifier(calculateProficiencyBonus(character.level))
            )

            SummaryInfoRow(
                labelRes = R.string.armor_class,
                value = character.armorClass.toString()
            )

            SummaryInfoRow(
                labelRes = R.string.hp,
                value = stringResource(
                    R.string.hit_points_value,
                    character.currentHitPoints,
                    character.maxHitPoints
                )
            )
        }

        CreationSection(R.string.appearance_traits) {
            SummaryTextSection(
                titleRes = R.string.appearance_selection,
                text = character.appearance
            )

            SummaryTextSection(
                titleRes = R.string.personality,
                text = character.personality
            )

            SummaryTextSection(
                titleRes = R.string.ideal,
                text = character.ideal
            )

            SummaryTextSection(
                titleRes = R.string.attachment,
                text = character.attachment
            )

            SummaryTextSection(
                titleRes = R.string.weakness,
                text = character.weakness
            )
        }

        CreationSection(R.string.backstory_selection) {
            SummaryTextSection(
                titleRes = R.string.description,
                text = character.backstory
            )
        }

        CreationSection(R.string.equipment_selection) {
            SummaryTextSection(
                titleRes = R.string.items,
                text = character.equipment
            )
        }

        CreationSection(R.string.additional_info_selection) {
            SummaryTextSection(
                titleRes = R.string.notes,
                text = character.additionalInfo
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun SummaryHero(
    name: String,
    level: Int,
    raceName: String?,
    className: String?,
    classType: ClassType,
    armorClass: Int,
    currentHitPoints: Int,
    maxHitPoints: Int
) {
    val classIcon = classType.toClassIcon()

    CustomCard {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(classIcon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.size(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = name.ifBlank {
                        stringResource(R.string.unknown)
                    },
                    color = TextPrimaryDark,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(
                        R.string.character_subtitle,
                        level,
                        raceName ?: stringResource(R.string.unknown_race),
                        className ?: stringResource(R.string.unknown_class)
                    ),
                    color = TextSecondaryDark,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SummaryChip(
                icon = Icons.Default.Star,
                text = stringResource(
                    R.string.character_card_level,
                    level
                ),
                modifier = Modifier.weight(1f)
            )

            SummaryChip(
                icon = Icons.Default.Security,
                text = stringResource(
                    R.string.character_card_ac,
                    armorClass
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SummaryChip(
                icon = Icons.Default.Favorite,
                text = stringResource(
                    R.string.character_card_hp,
                    currentHitPoints,
                    maxHitPoints
                ),
                modifier = Modifier.weight(1f)
            )

            SummaryChip(
                icon = Icons.Default.Star,
                text = stringResource(
                    R.string.character_card_pb,
                    textAsModifier(calculateProficiencyBonus(level))
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}