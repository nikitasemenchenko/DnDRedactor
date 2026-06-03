package com.example.dndredactor.presentation.diceRoller

import androidx.compose.runtime.Composable
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.AppTopBar

@Composable
fun DiceRollerTopBar(
    onBack: () -> Unit
) {
    AppTopBar(
        titleRes = R.string.dice_roller,
        onBack = onBack
    )
}