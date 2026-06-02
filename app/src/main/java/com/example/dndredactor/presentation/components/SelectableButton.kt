package com.example.dndredactor.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun SelectableButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LightButtonColor else ButtonColor,
            contentColor = if (isSelected) Color.Black else LightColor
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text)

            if (isSelected) {
                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}