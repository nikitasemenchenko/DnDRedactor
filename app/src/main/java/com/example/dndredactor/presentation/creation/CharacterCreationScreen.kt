package com.example.dndredactor.presentation.creation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CharacterCreationScreen(
    vm: CreationViewModel = hiltViewModel(),
    onReturn: () -> Unit,
) {
    val uiState by vm.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.events.collect { event ->
            when (event) {
                CreationEvent.CharacterSaved -> onReturn()

                is CreationEvent.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(event.message.resId)
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CharacterCreationTopBar(
                onBack = onReturn
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        bottomBar = {
            if (!uiState.loading) {
                CharacterCreationBottomBar(
                    onFinished = vm::saveCharacter,
                    currentStep = uiState.currentStep,
                    canGoNext = vm.canGoToNextStep(),
                    goBack = vm::goToPreviousStep,
                    goNext = vm::goToNextStep
                )
            }
        }
    ) { paddingValues ->
        CharacterCreationContent(
            paddingValues = paddingValues,
            uiState = uiState,
            vm = vm
        )
    }
}