package de.fejuma.impfi.model


data class Difficulty(
    val level: DifficultyLevel,
    val name: String,
    val fieldAmount: Int,
    val minesAmount: Int
)

val difficulties = listOf(
    Difficulty(
        DifficultyLevel.EASY,
        "Leicht",
        69,
        10
    ), //TODO: replace with string resource
    Difficulty(DifficultyLevel.NORMAL, "Mittel", 200, 20),
    Difficulty(DifficultyLevel.HARD, "Schwer", 400, 30)
)