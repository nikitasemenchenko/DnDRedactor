package com.example.dndredactor.presentation.characterEdit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dndredactor.presentation.theme.BackPurple
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun CharacterEditScreen(
    vm: CharacterEditViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onSaved: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.events.collect { event ->
            when (event) {
                CharacterEditEvent.CharacterUpdated -> onSaved()

                is CharacterEditEvent.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(event.message.resId)
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            CharacterEditTopBar(onBack = onBack)
        },
        bottomBar = {
            if (uiState is CharacterEditUiState.Success) {
                CharacterEditBottomBar(
                    onBack = onBack,
                    onSave = vm::saveCharacter
                )
            }
        },
        containerColor = BackPurple
    ) { contentPadding ->
        when (val state = uiState) {
            CharacterEditUiState.Loading -> {
                CharacterEditLoading(
                    modifier = Modifier.padding(contentPadding)
                )
            }

            is CharacterEditUiState.Success -> {
                CharacterEditContent(
                    modifier = Modifier.padding(contentPadding),
                    state = state,
                    vm = vm
                )
            }

            is CharacterEditUiState.Error -> {
                CharacterEditError(
                    modifier = Modifier.padding(contentPadding),
                    message = stringResource(state.message.resId)
                )
            }
        }
    }
}

@Composable
private fun CharacterEditLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CharacterEditError(
    modifier: Modifier = Modifier,
    message: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = LightColor,
            style = MaterialTheme.typography.titleLarge
        )
    }
}