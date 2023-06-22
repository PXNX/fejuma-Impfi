package de.fejuma.impfi.model

import androidx.compose.ui.graphics.Color


data class Difficulty(
    val name: String,
    val height: Int,
    val width: Int,
    val mines: Int,
    val difficultyColor: Color
)


enum class DifficultyLevel {
    EASY,
    NORMAL,
    HARD
}
