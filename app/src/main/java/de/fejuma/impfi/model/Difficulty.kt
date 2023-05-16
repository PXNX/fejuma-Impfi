package de.fejuma.impfi.model

import androidx.compose.ui.graphics.Color


data class Difficulty(
    val name: String,
    val height: Int,
    val width: Int,
    val mines: Int,
    val difficultyColor: Color
)

//TODO: replace with string resource
val difficulties = mapOf(
    DifficultyLevel.EASY to Difficulty("Leicht", 10, 10, 10, Color.Green),
    DifficultyLevel.NORMAL to Difficulty("Mittel", 16, 16, 40, Color.Yellow),
    DifficultyLevel.HARD to Difficulty("Schwer", 30, 16, 80, Color.Red)
)