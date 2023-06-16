package de.fejuma.impfi

import androidx.compose.ui.graphics.Color
import de.fejuma.impfi.model.Difficulty
import de.fejuma.impfi.model.DifficultyLevel

val difficulties = mapOf(
    DifficultyLevel.EASY to Difficulty("Leicht", 4, 4, 1, Color.Green),
    DifficultyLevel.NORMAL to Difficulty("Mittel", 16, 12, 25, Color.Yellow),
    DifficultyLevel.HARD to Difficulty("Schwer", 25, 16, 60, Color.Red)
)

const val timeLimit = 3_600