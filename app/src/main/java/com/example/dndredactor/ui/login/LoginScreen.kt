package com.example.dndredactor.ui.login

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dndredactor.R
import com.example.dndredactor.ui.AppViewModelProvider
import com.example.dndredactor.ui.theme.ButtonColor
import com.example.dndredactor.ui.theme.LightColor
import com.example.dndredactor.ui.theme.textButtonColor

@Composable
fun LoginScreen(
    vm: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onLoginSuccess: () -> Unit,
    goRegister: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val inputState by vm.inputState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            onLoginSuccess()
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
            style = MaterialTheme.typography.headlineSmall,
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
                visualTransformation = PasswordVisualTransformation()

            )

            if (uiState is LoginUiState.Error) {
                Text(
                    text = stringResource((uiState as LoginUiState.Error).message),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }
            Button(
                onClick = {
                    vm.onLogin()
                },
                enabled = uiState !is LoginUiState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor
                ),
                shape = MaterialTheme.shapes.large
            ) {
                if (uiState is LoginUiState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = stringResource(R.string.enter_dungeon),
                        style = MaterialTheme.typography.headlineSmall,
                        color = LightColor
                    )
                }
            }
            Text(
                text = stringResource(R.string.no_account),
                color = LightColor,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
            TextButton(
                onClick = { goRegister() }
            ) {
                Text(
                    text = stringResource(R.string.try_register),
                    style = MaterialTheme.typography.titleMedium,
                    color = textButtonColor
                )
            }
        }
    }

}

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
    leadingIcon: ImageVector,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(stringResource(label))
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = stringResource(label),
                tint = Color.Black
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = LightColor,
            focusedContainerColor = LightColor,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )
}