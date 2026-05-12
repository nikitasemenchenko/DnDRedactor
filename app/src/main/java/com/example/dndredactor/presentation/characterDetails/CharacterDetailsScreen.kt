package com.example.dndredactor.presentation.characterDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dndredactor.presentation.theme.BackPurple
import com.example.dndredactor.presentation.theme.LightButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun CharacterDetailsScreen(
    vm: CharacterDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            CharacterDetailsTopBar(onBackClick = onBack)
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
                    text = state.message
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
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Персонаж",
                color = LightColor,
                fontWeight = FontWeight.Medium
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = LightColor
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

        Text(
            text = "Уровень ${character.level}",
            color = LightColor,
            style = MaterialTheme.typography.bodyLarge
        )

        DetailsCard(title = "Основное") {
            Column {
                DetailRow(label = "Раса", value = state.raceName ?: "Неизвестно")
                DetailRow(label = "Подраса", value = state.subraceName ?: "Не выбрана")
                DetailRow(label = "Класс", value = state.className ?: "Неизвестно")
                DetailRow(label = "Архетип", value = state.archetypeName ?: "Не выбран")
            }
        }

        DetailsCard(title = "Внешность") {
            Text(
                text = character.appearance.ifBlank { "Не указано" },
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        DetailsCard(title = "Черты персонажа") {
            Column {
                DetailRow(label = "Характер", value = character.personality)
                DetailRow(label = "Идеал", value = character.ideal)
                DetailRow(label = "Привязанность", value = character.attachment)
                DetailRow(label = "Слабость", value = character.weakness)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
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
            text = value.ifBlank { "Не указано" },
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