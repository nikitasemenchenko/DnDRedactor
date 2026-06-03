package com.example.dndredactor.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun Title(
    @StringRes textRes: Int
) {
    Text(
        text = stringResource(textRes),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = LightColor
    )
}