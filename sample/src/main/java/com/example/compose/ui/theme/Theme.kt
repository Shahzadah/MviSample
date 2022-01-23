package com.example.compose.ui.theme

import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Charcol,
    secondary = Purple700,
    background = Charcol900,
    surface = Charcol900,
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    secondary = Purple700,
    background = Color.White,
    surface = Color.White,
)

@Composable
fun ComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    window: Window,
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    window.statusBarColor = if (darkTheme) Charcol500.hashCode() else Purple700.hashCode()

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}