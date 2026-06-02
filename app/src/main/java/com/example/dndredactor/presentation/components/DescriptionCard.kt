package com.example.dndredactor.presentation.components

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dndredactor.presentation.theme.LightButtonColor

@Composable
fun DescriptionCard(
    description: String?,
    @StringRes placeholderRes: Int,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor,
            contentColor = Color.Black
        ),
        shape = MaterialTheme.shapes.large,
        modifier = modifier.animateContentSize()
    ) {
        Text(
            text = description?.takeIf { it.isNotBlank() }
                ?: stringResource(placeholderRes),
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(8.dp),
            minLines = 3
        )
    }
}