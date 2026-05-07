package com.example.dndredactor.presentation.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dndredactor.R
import com.example.dndredactor.presentation.AppViewModelProvider


@Composable
fun StartScreen(
    vm: StartViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onAuthorized: () -> Unit,
    onUnauthorized: () -> Unit
) {
    val state = vm.state.collectAsState().value

    LaunchedEffect(state) {
        when (state) {
            is StartUiState.Authorized -> onAuthorized()
            is StartUiState.Unauthorized -> onUnauthorized()
            else -> {}
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is StartUiState.Loading -> {
                CircularProgressIndicator()
            }
            is StartUiState.Error -> {
                Error(
                    state.message,
                    vm::retryCheckAuth
                )
            }
            else -> {}
        }
    }
}

@Composable
fun Error(message: Int,
          onRetry: () -> Unit){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = stringResource(message),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onRetry
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}