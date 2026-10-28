package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ValetonColorScheme = darkColorScheme(
    primary = ValetonCyan,
    onPrimary = ValetonNavyDark,
    primaryContainer = ValetonNavySurface,
    onPrimaryContainer = ValetonCyan,
    secondary = ValetonElectricBlue,
    onSecondary = Color.White,
    secondaryContainer = ValetonNavyCard,
    onSecondaryContainer = ValetonCyanMuted,
    tertiary = ValetonAmberDrive,
    onTertiary = ValetonNavyDark,
    tertiaryContainer = Color(0xFF332000),
    onTertiaryContainer = ValetonAmberDrive,
    background = ValetonNavySuit,
    onBackground = TextPrimary,
    surface = ValetonNavySuit,
    onSurface = TextPrimary,
    surfaceVariant = ValetonNavyCard,
    onSurfaceVariant = TextSecondary,
    outline = ValetonNavyElevated,
    outlineVariant = Color(0xFF1E293B),
    error = ValetonFireRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Valeton suit pedal hardware look defaults to signature dark navy
    dynamicColor: Boolean = false, // Keep authentic Valeton signature aesthetic
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ValetonColorScheme,
        typography = Typography,
        content = content
    )
}
