package com.example.dndredactor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dndredactor.ui.creation.CharacterCreationScreen
import com.example.dndredactor.ui.login.LoginScreen
import com.example.dndredactor.ui.register.RegisterScreen
import com.example.dndredactor.ui.mainScreen.MainScreen
import com.example.dndredactor.ui.start.StartScreen


sealed class Screen(val route: String){
    object Start: Screen("start")
    object Login: Screen("login")
    object Registration: Screen("registration")
    object Main: Screen("main")
    object CharacterCreation : Screen("character_creation")
}
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        composable(Screen.Start.route) {
            StartScreen(
                onAuthorized = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                },
                onUnauthorized = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                goRegister = {
                    navController.navigate(Screen.Registration.route)
                }
            )
        }
        composable(Screen.Registration.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Registration.route) { inclusive = true }
                    }
                },
                goLogin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        composable(Screen.Main.route) {
            MainScreen(
                onCharacterClick = {},
                onCreateClick = {
                    navController.navigate(Screen.CharacterCreation.route)
                }
            )
        }
        composable(Screen.CharacterCreation.route) {

            CharacterCreationScreen(
                onFinished = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.CharacterCreation.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                goToMainScreen = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.CharacterCreation.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )

        }
    }
}