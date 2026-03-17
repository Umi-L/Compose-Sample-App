package com.julianogrady.sample.core.theming

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define custom light colors
val LightAppColors = lightColorScheme(
    primary = Color(0xFFe3678e),
    secondary = Color(0xFFe1678e),
    background = Color.White,
    primaryContainer = Color(0xFFe2dfeb),
    surface = Color(0xFFefebf3),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    surfaceDim = Color(0xFFd6d6dc),
    // ... other colors
)


@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Detect system dark theme preference
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        LightAppColors //DarkAppColors
    } else {
        LightAppColors
    }

    MaterialTheme(
        colorScheme = colors,
        // ... other theme parameters like typography and shapes
        content = content
    )
}