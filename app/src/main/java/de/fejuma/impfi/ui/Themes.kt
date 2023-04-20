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
    primary = black,
    onPrimary = white,
)


@Composable
fun MinesweeperTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) =   MaterialTheme(
       colorScheme =         if (useDarkTheme) {
           DarkColors
       } else {
           LightColors
       },
        content = content
    )

