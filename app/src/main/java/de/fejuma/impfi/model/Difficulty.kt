package de.fejuma.impfi.model




data class Difficulty(
    val level:DifficultyLevel,
    val name: String,
    val fieldAmount: Int,
    val minesAmount: Int
    )