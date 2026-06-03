package com.example.dndredactor.presentation.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Character
import com.example.dndredactor.presentation.components.CustomCard
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor
import com.example.dndredactor.presentation.theme.TextPrimaryDark

@Composable
fun MainContent(
    paddingValues: PaddingValues,
    uiState: MainScreenUiState,
    onCreateClick: () -> Unit,
    onDiceRollerClick: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    onDeleteRequest: (Character) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        MainHeroCard(
            onCreateClick = onCreateClick,
            onDiceRollerClick = onDiceRollerClick
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(R.string.your_characters),
            modifier = Modifier.fillMaxWidth(),
            color = LightColor,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        when (uiState) {
            MainScreenUiState.Loading -> {
                MainLoading()
            }

            is MainScreenUiState.Error -> {
                MainError(
                    message = stringResource(uiState.message.resId)
                )
            }

            is MainScreenUiState.Success -> {
                if (uiState.characters.isEmpty()) {
                    EmptyCharactersState()
                } else {
                    CharacterList(
                        characters = uiState.characters,
                        onDeleteRequest = onDeleteRequest,
                        onCharacterClick = onCharacterClick
                    )
                }
            }
        }
    }
}

@Composable
fun MainHeroCard(
    onCreateClick: () -> Unit,
    onDiceRollerClick: () -> Unit
) {
    CustomCard {
        Text(
            text = stringResource(R.string.main_hero_title),
            color = TextPrimaryDark,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.main_hero_description),
            color = TextPrimaryDark.copy(alpha = 0.78f),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCreateClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor,
                contentColor = LightColor
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = stringResource(R.string.create_character),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onDiceRollerClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = TextPrimaryDark
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Icon(
                imageVector = Icons.Default.Casino,
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = stringResource(R.string.open_dice_roller),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun MainLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = LightColor
        )
    }
}

@Composable
private fun MainError(
    message: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = LightColor,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}