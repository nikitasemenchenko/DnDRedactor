package com.example.dndredactor.presentation

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dndredactor.DndApplication
import com.example.dndredactor.presentation.creation.CreationViewModel
import com.example.dndredactor.presentation.login.LoginViewModel
import com.example.dndredactor.presentation.register.RegisterViewModel
import com.example.dndredactor.presentation.start.StartViewModel

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
            CreationViewModel(
                inventoryApplication().container.creationRepository
            )
        }

    }
}

fun CreationExtras.inventoryApplication(): DndApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DndApplication)