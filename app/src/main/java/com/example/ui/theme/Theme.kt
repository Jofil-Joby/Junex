package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ResilientSignalColorScheme = darkColorScheme(
    primary = WarmAmber,
    onPrimary = TextDarkOnAccent,
    primaryContainer = InkBlueElevated,
    onPrimaryContainer = TextOffWhite,
    secondary = ElectricCyan,
    onSecondary = TextDarkOnAccent,
    secondaryContainer = InkBlueElevated,
    onSecondaryContainer = ElectricCyan,
    tertiary = CoralRed,
    onTertiary = TextOffWhite,
    background = MidnightNavy,
    onBackground = TextOffWhite,
    surface = InkBlueSurface,
    onSurface = TextOffWhite,
    surfaceVariant = InkBlueElevated,
    onSurfaceVariant = TextMutedSlate,
    outline = InkBlueBorder,
    outlineVariant = InkBlueDivider,
    error = CoralRed,
    onError = TextOffWhite
)

@Composable
fun DisasterMeshTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ResilientSignalColorScheme,
        typography = Typography,
        content = content
    )
}

