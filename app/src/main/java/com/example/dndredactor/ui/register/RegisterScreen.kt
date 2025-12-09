package com.example.dndredactor.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dndredactor.R
import com.example.dndredactor.ui.AppViewModelProvider
import com.example.dndredactor.ui.login.AuthTextField
import com.example.dndredactor.ui.theme.ButtonColor
import com.example.dndredactor.ui.theme.LightColor
import com.example.dndredactor.ui.theme.textButtonColor


@Composable
fun RegisterScreen(
    vm: RegisterViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onRegisterSuccess: () -> Unit,
    goLogin: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    val inputState by vm.inputState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is RegisterUiState.Success) {
            onRegisterSuccess()
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.russian_app_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium,
            color = LightColor
        )
        Image(
            painter = painterResource(R.drawable.d20),
            contentDescription = null,
            modifier = Modifier.size(350.dp)
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(200.dp))
            AuthTextField(
                value = inputState.username,
                label = R.string.username,
                leadingIcon = Icons.Default.AccountCircle,
                onValueChange = vm::onUsernameChanged
            )
            AuthTextField(
                value = inputState.email,
                label = R.string.email,
                leadingIcon = Icons.Default.Email,
                onValueChange = vm::onEmailChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            AuthTextField(
                value = inputState.password,
                label = R.string.password,
                leadingIcon = Icons.Default.Lock,
                onValueChange = vm::onPasswordChanged,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

            )
            AuthTextField(
                value = inputState.repeatedPassword,
                label = R.string.password,
                leadingIcon = Icons.Default.Lock,
                onValueChange = vm::onRepeatPasswordChanged,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

            )
            if (uiState is RegisterUiState.Error) {
                Text(
                    text = stringResource((uiState as RegisterUiState.Error).message),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }
            Button(
                onClick = {
                    vm.onRegister()
                },
                enabled = uiState !is RegisterUiState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor
                ),
                shape = MaterialTheme.shapes.large
            ) {
                if (uiState is RegisterUiState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = stringResource(R.string.try_register),
                        style = MaterialTheme.typography.headlineSmall,
                        color = LightColor
                    )
                }

            }
            Text(
                text = stringResource(R.string.have_account),
                color = LightColor,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
            TextButton(
                onClick = { goLogin() }
            ) {
                Text(
                    text = stringResource(R.string.enter),
                    style = MaterialTheme.typography.titleMedium,
                    color = textButtonColor
                )
            }
        }
    }
}
