package com.example.dndredactor.presentation.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.data.model.Character
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor

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
        Spacer(modifier = Modifier.height(16.dp))

        MainActionButton(
            text = stringResource(R.string.create),
            onClick = onCreateClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        MainActionButton(
            text = stringResource(R.string.dice_roller),
            onClick = onDiceRollerClick
        )

        Spacer(modifier = Modifier.height(16.dp))

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
private fun MainActionButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = LightColor
        )
    }
}

@Composable
private fun MainLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
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