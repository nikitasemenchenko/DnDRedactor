package com.example.dndredactor.presentation.characterEdit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
fun CharacterEditBottomBar(
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    Surface(
        color = BackPurple,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = LightColor
                )
            }

            Button(
                onClick = onSave,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = stringResource(R.string.save),
                    color = LightColor
                )
            }
        }
    }
}