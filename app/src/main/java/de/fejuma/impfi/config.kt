package de.fejuma.impfi

import androidx.compose.ui.graphics.Color
import de.fejuma.impfi.model.Difficulty
import de.fejuma.impfi.model.DifficultyLevel

val difficulties = mapOf(
    DifficultyLevel.EASY to Difficulty("Leicht", 10, 10, 10, Color.Green),
    DifficultyLevel.NORMAL to Difficulty("Mittel", 16, 16, 40, Color.Yellow),
    DifficultyLevel.HARD to Difficulty("Schwer", 30, 16, 80, Color.Red)
)

const val timeLimit = 3_600