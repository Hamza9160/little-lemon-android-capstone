package com.littlelemon.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LittleLemonColorScheme = lightColorScheme(
    primary = LittleLemonGreen,
    onPrimary = White,
    secondary = LittleLemonYellow,
    onSecondary = CharcoalGray,
    tertiary = LittleLemonSalmon,
    background = White,
    onBackground = CharcoalGray,
    surface = White,
    onSurface = CharcoalGray,
    surfaceVariant = CloudGray
)

@Composable
fun LittleLemonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Brand identity stays consistent in light and dark contexts.
    MaterialTheme(
        colorScheme = LittleLemonColorScheme,
        typography = Typography,
        content = content
    )
}
