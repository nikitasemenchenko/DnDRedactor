package com.example.dndredactor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dndredactor.ui.authScreens.LoginDestination
import com.example.dndredactor.ui.authScreens.LoginScreen
import com.example.dndredactor.ui.authScreens.RegisterScreen
import com.example.dndredactor.ui.authScreens.RegistrationDestination
import com.example.dndredactor.ui.mainScreen.MainScreen
import com.example.dndredactor.ui.mainScreen.MainScreenDestination
import com.example.dndredactor.ui.start.StartDestination
import com.example.dndredactor.ui.start.StartScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = StartDestination.route,
        modifier = modifier
    ) {
        composable(StartDestination.route) {
            StartScreen(
                onAuthorized = {
                    navController.navigate(MainScreenDestination.route) {
                        popUpTo(StartDestination.route) { inclusive = true }
                    }
                },
                onUnauthorized = {
                    navController.navigate("login") {
                        popUpTo(StartDestination.route) { inclusive = true }
                    }
                }
            )
        }

        composable(LoginDestination.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(MainScreenDestination.route) {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
                },
                goRegister = {
                    navController.navigate(RegistrationDestination.route)
                }
            )
        }
        composable(RegistrationDestination.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(MainScreenDestination.route) {
                        popUpTo(RegistrationDestination.route) { inclusive = true }
                    }
                },
                goLogin = {
                    navController.navigate(LoginDestination.route)
                }
            )
        }
        composable(MainScreenDestination.route) {
            MainScreen(
                onCharacterClick = {},
                onCreateClick = {}
            )
        }
    }
}