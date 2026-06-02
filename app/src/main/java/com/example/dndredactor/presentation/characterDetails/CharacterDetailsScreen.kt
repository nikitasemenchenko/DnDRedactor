package com.example.dndredactor.presentation.characterDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.calculateProficiencyBonus
import com.example.dndredactor.data.model.getModifier
import com.example.dndredactor.data.model.textAsModifier
import com.example.dndredactor.presentation.theme.BackPurple
import com.example.dndredactor.presentation.theme.LightButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun CharacterDetailsScreen(
    vm: CharacterDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            CharacterDetailsTopBar(
                onBackClick = onBack,
                onEditClick = {
                    val state = uiState
                    if (state is CharacterDetailsUiState.Success) {
                        onEdit(state.character.id)
                    }
                }
            )
        },
        containerColor = BackPurple
    ) { paddingValues ->
        when (val state = uiState) {
            CharacterDetailsUiState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is CharacterDetailsUiState.Error -> {
                DetailsMessage(
                    modifier = Modifier.padding(paddingValues),
                    text = stringResource(state.message.resId)
                )
            }

            is CharacterDetailsUiState.Success -> {
                CharacterDetailsContent(
                    modifier = Modifier.padding(paddingValues),
                    state = state
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterDetailsTopBar(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.character),
                color = LightColor,
                fontWeight = FontWeight.Medium
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = LightColor
                )
            }
        },
        actions = {
            IconButton(
                onClick = onEditClick
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_character)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BackPurple
        )
    )
}

@Composable
private fun CharacterDetailsContent(
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
                text = character.backstory.ifBlank { stringResource(R.string.not_specified) },
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
                    value = character.armorClass.toString())
                DetailRow(
                    label = stringResource(R.string.hp),
                    value = "${character.currentHitPoints}/${character.maxHitPoints}"
                )
            }
        }

        DetailsCard(title = stringResource(R.string.equipment_selection)) {
            Text(
                text = character.equipment.ifBlank { stringResource(R.string.not_specified) },
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        DetailsCard(title = stringResource(R.string.appearance_selection)) {
            Text(
                text = character.appearance.ifBlank { stringResource(R.string.not_specified) },
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
                text = character.additionalInfo.ifBlank { stringResource(R.string.not_specified) },
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun DetailsCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            content()
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = value.ifBlank { stringResource(R.string.not_specified)},
            color = Color.Black
        )
    }
}

@Composable
private fun DetailsMessage(
    modifier: Modifier = Modifier,
    text: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = LightColor,
            style = MaterialTheme.typography.titleLarge
        )
    }
}