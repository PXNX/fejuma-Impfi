package de.fejuma.impfi.model

import de.fejuma.impfi.presentation.game.MineFieldState

data class TileState(
    val x:Int,
    val y:Int,
    var isFlagged: Boolean = false,
    val isShown: Boolean = false,
    val isMine: Boolean = false,
    val nearbyMines: Int? = null
)

fun TileState.getFieldState(): MineFieldState = if (this.isShown) {
    if (this.isMine) MineFieldState.VIRUS
    else MineFieldState.NUMBER
} else {
    if (this.isFlagged) MineFieldState.FLAG
    else MineFieldState.COVERED
}


