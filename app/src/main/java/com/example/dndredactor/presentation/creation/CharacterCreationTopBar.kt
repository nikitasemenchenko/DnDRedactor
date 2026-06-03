package com.example.dndredactor.presentation.creation

import androidx.compose.runtime.Composable
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.AppTopBar

@Composable
fun CharacterCreationTopBar(
    onBack: () -> Unit
) {
    AppTopBar(
        titleRes = R.string.create_character,
        onBack = onBack
    )
}