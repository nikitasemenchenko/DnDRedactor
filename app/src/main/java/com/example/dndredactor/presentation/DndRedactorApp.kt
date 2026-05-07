package com.example.dndredactor.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.dndredactor.presentation.navigation.AppNavHost

@Composable
fun DndRedactorApp() {
    val navController = rememberNavController()
    AppNavHost(navController)
}