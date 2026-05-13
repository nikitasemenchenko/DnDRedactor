package com.example.dndredactor.presentation.creation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dndredactor.R
import com.example.dndredactor.presentation.creation.steps.AbilityScoresScreen
import com.example.dndredactor.presentation.creation.steps.ClassSelectionScreen
import com.example.dndredactor.presentation.creation.steps.CreationSummaryScreen
import com.example.dndredactor.presentation.creation.steps.HumanTraitsSelectionScreen
import com.example.dndredactor.presentation.creation.steps.RaceSelectionScreen
import com.example.dndredactor.presentation.theme.BackPurple
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun CharacterCreationScreen(
    vm: CreationViewModel = hiltViewModel(),
    onReturn: () -> Unit,
) {
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vm.events.collect { event ->
            when (event) {
                CreationEvent.CharacterSaved -> onReturn()
                is CreationEvent.ShowError -> {
                    // Snackbar добавить
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CharacterCreationTopBar(onReturn)
        },
        bottomBar = {
            CharacterCreationBottomBar(
                onFinished = vm::saveCharacter,
                currentStep = uiState.currentStep,
                canGoNext = vm.canGoToNextStep(),
                goBack = vm::goToPreviousStep,
                goNext = vm::goToNextStep
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding)) {

            when (uiState.currentStep) {
                CreationStep.RACE -> RaceSelectionScreen(vm = vm)
                CreationStep.CLASS -> ClassSelectionScreen(vm = vm)
                CreationStep.HUMAN_TRAITS -> HumanTraitsSelectionScreen(vm = vm)
                CreationStep.ABILITY_SCORES -> AbilityScoresScreen(vm = vm)
                CreationStep.FINAL -> CreationSummaryScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCreationTopBar(onBack: () -> Unit){
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(R.string.create_character),
                color = LightColor,
                fontWeight = FontWeight.Medium)
        },
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BackPurple
        )
    )
}

@Composable
fun CharacterCreationBottomBar(
    onFinished: () -> Unit,
    currentStep: CreationStep,
    canGoNext: Boolean,
    goBack: () -> Unit,
    goNext: () -> Unit
) {
    Surface(
        color = BackPurple,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentStep == CreationStep.RACE) {
                Button(
                    onClick = { goNext() },
                    enabled = canGoNext,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(stringResource(R.string.next), color = LightColor)
                }
            } else {
                Button(
                    onClick = { goBack() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(stringResource(R.string.back), color = LightColor)
                }

                Button(
                    onClick = {
                        if (currentStep == CreationStep.FINAL) onFinished()
                        else goNext()
                    },
                    enabled = canGoNext,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        text = if (currentStep == CreationStep.FINAL)
                            stringResource(R.string.create)
                        else stringResource(R.string.next),
                        color = LightColor
                    )
                }
            }
        }
    }
}