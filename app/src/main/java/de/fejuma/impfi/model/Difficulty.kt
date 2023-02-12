package de.fejuma.impfi.model

import androidx.compose.ui.graphics.Color


data class Difficulty(
    val level: DifficultyLevel,
    val name: String,
    val fieldAmount: Int,
    val minesAmount: Int,
    val difficultyColor: Color
)

//TODO: replace with string resource
val difficulties = listOf(
    Difficulty(DifficultyLevel.EASY, "Leicht", 69, 10, Color.Green),
    Difficulty(DifficultyLevel.NORMAL, "Mittel", 200, 20, Color.Yellow),
    Difficulty(DifficultyLevel.HARD, "Schwer", 400, 30, Color.Red)
)