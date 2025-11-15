package com.example.dndredactor.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.dndredactor.ui.navigation.AppNavHost

@Composable
fun DndRedactorApp() {
    val navController = rememberNavController()
    AppNavHost(navController)
}