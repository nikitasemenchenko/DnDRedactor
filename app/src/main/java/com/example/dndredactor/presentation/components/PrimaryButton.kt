package com.example.dndredactor.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor,
            contentColor = LightColor
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}