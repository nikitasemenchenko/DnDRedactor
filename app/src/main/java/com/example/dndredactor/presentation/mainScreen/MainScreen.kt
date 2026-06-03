package com.example.dndredactor.presentation.mainScreen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dndredactor.data.model.Character
import com.example.dndredactor.presentation.theme.BackPurple

@Composable
fun MainScreen(
    vm: MainViewModel = hiltViewModel(),
    onCharacterClick: (Int) -> Unit,
    onCreateClick: () -> Unit,
    onDiceRollerClick: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    var characterDeleteDialog by remember {
        mutableStateOf<Character?>(null)
    }

    Scaffold(
        topBar = {
            MainTopBar()
        },
        containerColor = BackPurple
    ) { paddingValues ->
        MainContent(
            paddingValues = paddingValues,
            uiState = uiState,
            onCreateClick = onCreateClick,
            onDiceRollerClick = onDiceRollerClick,
            onCharacterClick = onCharacterClick,
            onDeleteRequest = { character ->
                characterDeleteDialog = character
            }
        )
    }

    characterDeleteDialog?.let { character ->
        DeleteCharacterDialog(
            characterName = character.name,
            onConfirm = {
                vm.deleteCharacter(character.id)
                characterDeleteDialog = null
            },
            onDismiss = {
                characterDeleteDialog = null
            }
        )
    }
}