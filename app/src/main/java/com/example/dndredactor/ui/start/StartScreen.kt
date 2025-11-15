package com.example.dndredactor.ui.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dndredactor.ui.AppViewModelProvider
import com.example.dndredactor.ui.navigation.NavigationDestination

object StartDestination : NavigationDestination {
    override val route = "start"
}

@Composable
fun StartScreen(
    vm: StartViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onAuthorized: () -> Unit,
    onUnauthorized: () -> Unit
) {
    val state = vm.state.collectAsState().value

    LaunchedEffect(state) {
        when (state) {
            is StartState.Authorized -> onAuthorized()
            is StartState.Unauthorized -> onUnauthorized()
            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}