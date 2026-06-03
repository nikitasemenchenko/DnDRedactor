package com.example.dndredactor.presentation.diceRoller

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.AppBottomBar
import com.example.dndredactor.presentation.components.AppBottomBarButton

@Composable
fun DiceRollerBottomBar(
    onRollClick: () -> Unit
) {
    AppBottomBar {
        AppBottomBarButton(
            textRes = R.string.roll_dice,
            onClick = onRollClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}