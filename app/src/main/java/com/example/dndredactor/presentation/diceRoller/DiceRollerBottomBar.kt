package com.example.dndredactor.presentation.diceRoller

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.presentation.theme.BackPurple
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun DiceRollerBottomBar(
    onRollClick: () -> Unit
) {
    Surface(
        color = BackPurple,
        tonalElevation = 4.dp
    ) {
        Button(
            onClick = onRollClick,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = stringResource(R.string.roll_dice),
                color = LightColor,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}