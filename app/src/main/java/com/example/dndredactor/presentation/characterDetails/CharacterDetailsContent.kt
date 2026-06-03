package com.example.dndredactor.presentation.characterDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.getModifier
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.mappers.toClassIcon
import com.example.dndredactor.presentation.theme.TextPrimaryDark
import com.example.dndredactor.presentation.theme.TextSecondaryDark

@Composable
fun CharacterDetailsContent(
    modifier: Modifier = Modifier,
    state: CharacterDetailsUiState.Success
) {
    val character = state.character

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        CharacterDetailsHero(state)

        DetailsCard(title = stringResource(R.string.main)) {
            DetailRow(
                label = stringResource(R.string.character_gender),
                value = stringResource(character.gender.titleRes)
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
        }

        DetailsCard(title = stringResource(R.string.characteristics)) {
            Ability.entries.forEach { ability ->
                val score = character.abilityScores.get(ability)
                val modifier = character.abilityScores.getModifier(ability)

                DetailRow(
                    label = stringResource(ability.titleRes),
                    value = stringResource(
                        R.string.ability_score_value,
                        score,
                        textAsModifier(modifier)
                    )
                )
            }
        }

        DetailsCard(title = stringResource(R.string.backstory_selection)) {
            DetailsTextBlock(
                text = character.backstory
            )
        }

        DetailsCard(title = stringResource(R.string.equipment_selection)) {
            DetailsTextBlock(
                text = character.equipment
            )
        }

        DetailsCard(title = stringResource(R.string.appearance_selection)) {
            DetailsTextBlock(
                text = character.appearance
            )
        }

        DetailsCard(title = stringResource(R.string.character_traits)) {
            DetailTextSection(
                titleRes = R.string.personality,
                text = character.personality
            )

            Spacer(modifier = Modifier.height(8.dp))

            DetailTextSection(
                titleRes = R.string.ideal,
                text = character.ideal
            )

            Spacer(modifier = Modifier.height(8.dp))

            DetailTextSection(
                titleRes = R.string.attachment,
                text = character.attachment
            )

            Spacer(modifier = Modifier.height(8.dp))

            DetailTextSection(
                titleRes = R.string.weakness,
                text = character.weakness
            )
        }

        DetailsCard(title = stringResource(R.string.additional_info_selection)) {
            DetailsTextBlock(
                text = character.additionalInfo
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun CharacterDetailsHero(
    state: CharacterDetailsUiState.Success
) {
    val character = state.character
    val classIcon = character.classType.toClassIcon()

    DetailsCard {
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
                    text = character.name.ifBlank {
                        stringResource(R.string.unknown)
                    },
                    color = TextPrimaryDark,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(
                        R.string.character_subtitle,
                        character.level,
                        character.raceName ?: stringResource(R.string.unknown_race),
                        character.className ?: stringResource(R.string.unknown_class)
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
            CharacterChip(
                icon = Icons.Default.Star,
                text = stringResource(
                    R.string.character_card_level,
                    character.level
                ),
                modifier = Modifier.weight(1f)
            )

            CharacterChip(
                icon = Icons.Default.Security,
                text = stringResource(
                    R.string.character_card_ac,
                    character.armorClass
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CharacterChip(
                icon = Icons.Default.Favorite,
                text = stringResource(
                    R.string.character_card_hp,
                    character.currentHitPoints,
                    character.maxHitPoints
                ),
                modifier = Modifier.weight(1f)
            )

            CharacterChip(
                icon = Icons.Default.Star,
                text = stringResource(
                    R.string.character_card_pb,
                    textAsModifier(calculateProficiencyBonus(character.level))
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}