package com.example.dndredactor.presentation.characterEdit

import androidx.compose.runtime.Composable
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.AppTopBar

@Composable
fun CharacterEditTopBar(
    onBack: () -> Unit
) {
    AppTopBar(
        titleRes = R.string.edit_character,
        onBack = onBack
    )
}