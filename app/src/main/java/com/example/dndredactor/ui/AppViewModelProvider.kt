package com.example.dndredactor.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dndredactor.DndApplication
import com.example.dndredactor.ui.creation.CreationViewModel
import com.example.dndredactor.ui.login.LoginViewModel
import com.example.dndredactor.ui.register.RegisterViewModel
import com.example.dndredactor.ui.mainScreen.MainViewModel
import com.example.dndredactor.ui.start.StartViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                inventoryApplication().container.authRepository
            )
        }
        initializer {
            RegisterViewModel(
                inventoryApplication().container.authRepository
            )
        }
        initializer {
            StartViewModel(
                inventoryApplication().container.authRepository,
                inventoryApplication().container.tokenStorage
            )
        }
        initializer {
            MainViewModel(
                inventoryApplication().container.characterRepository,
                inventoryApplication().container.authRepository,
                inventoryApplication().container.tokenStorage
            )
        }

        initializer {
            CreationViewModel(
                inventoryApplication().container.creationRepository
            )
        }

    }
}

fun CreationExtras.inventoryApplication(): DndApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DndApplication)