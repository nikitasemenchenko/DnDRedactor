package com.example.dndredactor.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun ReadOnlyInfo(
    title: String,
    value: String
) {
    Text(
        text = "$title: $value",
        color = LightColor,
        style = MaterialTheme.typography.bodyLarge
    )
}