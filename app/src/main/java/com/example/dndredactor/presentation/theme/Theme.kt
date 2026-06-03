package com.example.dndredactor.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = ButtonColor,
    secondary = LightButtonColor,
    background = BackPurple,
    surface = SurfacePurple,
    onPrimary = LightColor,
    onSecondary = TextPrimaryDark,
    onBackground = LightColor,
    onSurface = LightColor,
    error = DangerColor
)

@Composable
fun DnDRedactorTheme(
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme.copy(background = BackPurple)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}