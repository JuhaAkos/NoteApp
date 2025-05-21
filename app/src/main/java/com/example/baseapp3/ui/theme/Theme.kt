package com.example.baseapp3.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.White,
    background = DarkGray,
    secondary = DarkGrayCover,
    onBackground = Color.White,
    surface = LightBlue,
    onSurface = DarkGray
)

private val LightColorPalette = darkColors(
    primary = Color.White,
    background = LightGray,
    secondary = LightGrayCover,
    onBackground = Color.Black,
    surface = LightBlue,
    onSurface = DarkGray
)

@Composable
fun NoteAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}