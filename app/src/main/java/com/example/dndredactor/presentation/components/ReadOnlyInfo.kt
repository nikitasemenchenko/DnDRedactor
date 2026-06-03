package com.example.dndredactor.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.dndredactor.R
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun ReadOnlyInfo(
    title: String,
    value: String
) {
    Text(
        text = stringResource(R.string.readonly_info_value, title, value),
        color = LightColor,
        style = MaterialTheme.typography.bodyLarge
    )
}