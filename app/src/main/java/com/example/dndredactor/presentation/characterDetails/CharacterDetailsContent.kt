package com.example.dndredactor.presentation.characterDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.getModifier
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun CharacterDetailsContent(
    modifier: Modifier = Modifier,
    state: CharacterDetailsUiState.Success
) {
    val character = state.character
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = character.name,
            color = LightColor,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        DetailsCard(title = stringResource(R.string.main)) {
            Column {
                DetailRow(
                    label = stringResource(R.string.character_gender),
                    value = stringResource(character.gender.titleRes)
                )

                DetailRow(
                    label = stringResource(R.string.level),
                    value = character.level.toString()
                )

                DetailRow(
                    label = stringResource(R.string.race),
                    value = character.raceName ?: stringResource(R.string.not_selected)
                )

                DetailRow(
                    label = stringResource(R.string.subrace),
                    value = character.subraceName ?: stringResource(R.string.not_selected)
                )

                DetailRow(
                    label = stringResource(R.string.class_name),
                    value = character.className ?: stringResource(R.string.not_selected)
                )

                DetailRow(
                    label = stringResource(R.string.archetype),
                    value = character.archetypeName ?: stringResource(R.string.not_selected)
                )

                DetailRow(
                    label = stringResource(R.string.proficiency_bonus),
                    value = textAsModifier(calculateProficiencyBonus(character.level))
                )
            }
        }

        DetailsCard(title = stringResource(R.string.backstory_selection)) {
            Text(
                text = character.backstory.ifBlank {
                    stringResource(R.string.not_specified)
                },
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        DetailsCard(title = stringResource(R.string.characteristics)) {
            Column {
                Ability.entries.forEach { ability ->
                    val score = character.abilityScores.get(ability)
                    val modifier = character.abilityScores.getModifier(ability)

                    DetailRow(
                        label = stringResource(ability.titleRes),
                        value = "$score (${textAsModifier(modifier)})"
                    )
                }
            }
        }

        DetailsCard(title = stringResource(R.string.combat_stats_selection)) {
            Column {
                DetailRow(
                    label = stringResource(R.string.armor_class),
                    value = character.armorClass.toString()
                )

                DetailRow(
                    label = stringResource(R.string.hp),
                    value = "${character.currentHitPoints}/${character.maxHitPoints}"
                )
            }
        }

        DetailsCard(title = stringResource(R.string.equipment_selection)) {
            Text(
                text = character.equipment.ifBlank {
                    stringResource(R.string.not_specified)
                },
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        DetailsCard(title = stringResource(R.string.appearance_selection)) {
            Text(
                text = character.appearance.ifBlank {
                    stringResource(R.string.not_specified)
                },
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        DetailsCard(title = stringResource(R.string.character_traits)) {
            Column {
                DetailRow(
                    label = stringResource(R.string.personality),
                    value = character.personality
                )

                DetailRow(
                    label = stringResource(R.string.ideal),
                    value = character.ideal
                )

                DetailRow(
                    label = stringResource(R.string.attachment),
                    value = character.attachment
                )

                DetailRow(
                    label = stringResource(R.string.weakness),
                    value = character.weakness
                )
            }
        }

        DetailsCard(title = stringResource(R.string.additional_info_selection)) {
            Text(
                text = character.additionalInfo.ifBlank {
                    stringResource(R.string.not_specified)
                },
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}