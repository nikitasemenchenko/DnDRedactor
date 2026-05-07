package com.example.dndredactor.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dndredactor.presentation.creation.CharacterCreationScreen
import com.example.dndredactor.presentation.mainScreen.MainScreen


sealed class Screen(val route: String){
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