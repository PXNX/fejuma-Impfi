package de.fejuma.impfi.model

import de.fejuma.impfi.presentation.game.MineFieldState

data class Cell(
    var isFlagged: Boolean = false,
    var isShown: Boolean = false,
    var isMine: Boolean = false,
    var nearbyMines: Int = 0
)

fun Cell.getFieldState(): MineFieldState = if (this.isShown) {
    if (this.isMine) MineFieldState.VIRUS
    else MineFieldState.NUMBER
} else {
    if (this.isFlagged) MineFieldState.FLAG
    else MineFieldState.COVERED
}


