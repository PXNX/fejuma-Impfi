package de.fejuma.impfi.model

import androidx.compose.ui.graphics.Color

// A data class is basically like a POJO with extra steps, you use it to group data that belongs
// to one another together.
data class Difficulty(
    val nameResource: Int,
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
