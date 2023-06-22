package de.fejuma.impfi

import android.content.res.Resources
import androidx.compose.ui.graphics.Color
import de.fejuma.impfi.model.Difficulty
import de.fejuma.impfi.model.DifficultyLevel


val difficulties = mapOf(
    DifficultyLevel.EASY to Difficulty(Resources.getSystem().getString(R.string.difficulty_easy), 9, 6, 10, Color.Green),
    DifficultyLevel.NORMAL to Difficulty(Resources.getSystem().getString(R.string.difficulty_medium), 16, 10, 35, Color.Yellow),
    DifficultyLevel.HARD to Difficulty(Resources.getSystem().getString(R.string.difficulty_hard), 24, 16, 60, Color.Red)
)

const val timeLimit = 3_600