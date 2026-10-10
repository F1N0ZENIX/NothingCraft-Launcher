package com.nothingtube.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NothingColorScheme = darkColorScheme(
    primary = NothingWhite,
    onPrimary = NothingBlack,
    secondary = NothingGray,
    onSecondary = NothingWhite,
    background = NothingBlack,
    onBackground = NothingWhite,
    surface = NothingBlack,
    onSurface = NothingWhite,
    surfaceVariant = NothingDarkGray,
    onSurfaceVariant = NothingGray,
    error = NothingRed
)

@Composable
fun NothingTubeTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = NothingColorScheme, typography = NothingTypography, content = content)
}
