package com.example.dndredactor.presentation.diceRoller

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dndredactor.presentation.theme.BackPurple

@Composable
fun DiceRollerScreen(
    vm: DiceRollerViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            DiceRollerTopBar(
                onBack = onBack
            )
        },
        bottomBar = {
            DiceRollerBottomBar(
                onRollClick = vm::rollDice
            )
        },
        containerColor = BackPurple
    ) { contentPadding ->
        DiceRollerContent(
            modifier = Modifier.padding(contentPadding),
            state = uiState,
            vm = vm
        )
    }
}