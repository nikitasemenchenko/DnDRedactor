package com.example.dndredactor.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import com.example.dndredactor.presentation.theme.BackPurple
import com.example.dndredactor.presentation.theme.ButtonColor
import com.example.dndredactor.presentation.theme.LightColor

@Composable
fun AppBottomBar(
    content: @Composable RowScope.() -> Unit
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
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@Composable
fun AppBottomBarButton(
    @StringRes textRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor,
            contentColor = LightColor
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = stringResource(textRes),
            style = MaterialTheme.typography.titleMedium,
            color = LightColor
        )
    }
}