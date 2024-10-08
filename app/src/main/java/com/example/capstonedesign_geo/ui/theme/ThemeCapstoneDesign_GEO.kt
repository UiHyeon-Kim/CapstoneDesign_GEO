package com.example.capstonedesign_geo.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColors(
    primary = Color(0xFF4F89F8),
    primaryVariant = Color(0xFF4F89F8),
    secondary = Color(0xFFAECAFF)
)

@Composable
fun ThemeCapstoneDesign_GEO (content: @Composable () -> Unit)  {
    MaterialTheme(
        colors = LightColors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}