package de.fejuma.impfi

import androidx.compose.ui.graphics.Color
import de.fejuma.impfi.model.Difficulty
import de.fejuma.impfi.model.DifficultyLevel


val difficulties = mapOf(
    DifficultyLevel.EASY to Difficulty(
        R.string.difficulty_easy,
        10,
        10,
        10,
        Color.Green
    ),
    DifficultyLevel.NORMAL to Difficulty(
        R.string.difficulty_medium,
        16,
        10,
        30,
        Color.Yellow
    ),
    DifficultyLevel.HARD to Difficulty(
        R.string.difficulty_hard,
        24,
        16,
        60,
        Color.Red
    )
)

const val timeLimit = 3_600

