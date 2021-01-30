package com.mb.hunters.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

private val LightThemeColors = lightColors(
    primary = WildSand,
    primaryVariant = Alto,
    onPrimary = MineShaft,
    secondary = PersianRed,
    secondaryVariant = Alto,
    onSecondary = Boulder
)

private val DarkThemeColors = darkColors(
    primary = WildSand,
    primaryVariant = Alto,
    onPrimary = MineShaft,
    secondary = PersianRed,
    onSecondary = Boulder
)

@Composable
fun HuntersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    ProvideWindowInsets {
        MaterialTheme(
            colors = if (darkTheme) DarkThemeColors else LightThemeColors,
            content = content
        )
    }
}
