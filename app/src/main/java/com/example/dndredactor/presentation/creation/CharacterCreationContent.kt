package com.example.dndredactor.presentation.creation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.dndredactor.presentation.components.ErrorRetry
import com.example.dndredactor.presentation.creation.steps.AbilityGenerationMethodScreen
import com.example.dndredactor.presentation.creation.steps.AdditionalInfoScreen
import com.example.dndredactor.presentation.creation.steps.BackstoryScreen
import com.example.dndredactor.presentation.creation.steps.ClassSelectionScreen
import com.example.dndredactor.presentation.creation.steps.CombatStatsScreen
import com.example.dndredactor.presentation.creation.steps.CreationSummaryScreen
import com.example.dndredactor.presentation.creation.steps.EquipmentScreen
import com.example.dndredactor.presentation.creation.steps.PointBuyAbilityScoresScreen
import com.example.dndredactor.presentation.creation.steps.RaceSelectionScreen
import com.example.dndredactor.presentation.creation.steps.RandomAbilityScoresScreen
import com.example.dndredactor.presentation.creation.steps.TraitsSelectionScreen

@Composable
fun CharacterCreationContent(
    paddingValues: PaddingValues,
    uiState: CreationUiState,
    vm: CreationViewModel
) {
    when {
        uiState.loading -> {
            CharacterCreationLoading(
                modifier = Modifier.padding(paddingValues)
            )
        }

        uiState.error != null &&
                uiState.races.isEmpty() &&
                uiState.classes.isEmpty() -> {
            ErrorRetry(
                modifier = Modifier.padding(paddingValues),
                message = stringResource(uiState.error.resId),
                onRetry = vm::retryInitialDataLoading
            )
        }

        else -> {
            CharacterCreationStepContent(
                modifier = Modifier.padding(paddingValues),
                currentStep = uiState.currentStep,
                vm = vm
            )
        }
    }
}

@Composable
private fun CharacterCreationLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CharacterCreationStepContent(
    modifier: Modifier = Modifier,
    currentStep: CreationStep,
    vm: CreationViewModel
) {
    Column(
        modifier = modifier
    ) {
        when (currentStep) {
            CreationStep.RACE -> RaceSelectionScreen(vm = vm)

            CreationStep.CLASS -> ClassSelectionScreen(vm = vm)

            CreationStep.BACKSTORY -> BackstoryScreen(vm = vm)

            CreationStep.TRAITS -> TraitsSelectionScreen(vm = vm)

            CreationStep.ABILITY_GENERATION_METHOD -> {
                AbilityGenerationMethodScreen(vm = vm)
            }

            CreationStep.RANDOM_ABILITIES -> {
                RandomAbilityScoresScreen(vm = vm)
            }

            CreationStep.POINT_BUY_ABILITIES -> {
                PointBuyAbilityScoresScreen(vm = vm)
            }

            CreationStep.COMBAT_STATS -> CombatStatsScreen(vm = vm)

            CreationStep.EQUIPMENT -> EquipmentScreen(vm = vm)

            CreationStep.ADDITIONAL_INFO -> AdditionalInfoScreen(vm = vm)

            CreationStep.FINAL -> CreationSummaryScreen(vm = vm)
        }
    }
}