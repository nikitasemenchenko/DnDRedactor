package com.example.dndredactor.presentation.characterDetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dndredactor.presentation.theme.BackPurple

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
                CharacterDetailsLoading(
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is CharacterDetailsUiState.Error -> {
                CharacterDetailsError(
                    modifier = Modifier.padding(paddingValues),
                    message = stringResource(state.message.resId)
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