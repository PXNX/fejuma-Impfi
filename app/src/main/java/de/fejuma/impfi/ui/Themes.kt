package de.fejuma.impfi.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColors = lightColorScheme(
    primary = darkGreen,
    onPrimary = white,
)
private val DarkColors = darkColorScheme(
    primary = purple_200,
    onPrimary = white,
)

// Changing the colors based onn device settings. We could also change typography, shapes and
// dimensions here to centrally influence the look of our app. If we want to apply our defined
// themes, we have to wrap a Composable with it.
@Composable
fun MinesweeperTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) = MaterialTheme(
    colorScheme = if (useDarkTheme) {
        DarkColors
    } else {
        LightColors
    },
    content = content
)

