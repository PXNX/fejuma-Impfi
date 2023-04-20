package de.fejuma.impfi.presentation.game

enum class MineFieldState {
    COVERED,
    VIRUS,
    BLANK,
    NUMBER, //maybe using a sealed class is better here?
    FLAG
}