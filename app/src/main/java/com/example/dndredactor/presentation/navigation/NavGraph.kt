package com.example.dndredactor.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dndredactor.presentation.characterDetails.CharacterDetailsScreen
import com.example.dndredactor.presentation.creation.CharacterCreationScreen
import com.example.dndredactor.presentation.mainScreen.MainScreen
import kotlinx.serialization.Serializable


sealed interface AppRoute {
    @Serializable
    data object Main: AppRoute

    @Serializable
    data object CharacterCreation: AppRoute

    @Serializable
    data class CharacterDetails(val id: Int): AppRoute
}
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Main,
        modifier = modifier
    ) {
        composable<AppRoute.Main>{
            MainScreen(
                onCharacterClick = { id ->
                    navController.navigate(AppRoute.CharacterDetails(id))
                },
                onCreateClick = {
                    navController.navigate(AppRoute.CharacterCreation)
                }
            )
        }

        composable<AppRoute.CharacterCreation> {
            CharacterCreationScreen(
                onReturn = {
                    navController.popBackStack()
                }
            )
        }

        composable<AppRoute.CharacterDetails> {
            CharacterDetailsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}