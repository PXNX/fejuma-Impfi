package de.fejuma.impfi

import androidx.compose.ui.graphics.Color
import de.fejuma.impfi.model.Difficulty
import de.fejuma.impfi.model.DifficultyLevel

val difficulties = mapOf(
    DifficultyLevel.EASY to Difficulty("Leicht", 9, 6, 10, Color.Green),
    DifficultyLevel.NORMAL to Difficulty("Mittel", 16, 10, 35, Color.Yellow),
    DifficultyLevel.HARD to Difficulty("Schwer", 24, 16, 60, Color.Red)
)

const val timeLimit = 3_600