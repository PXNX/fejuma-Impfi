package de.fejuma.impfi.presentation.game.game

sealed class Tile(
    open val coverMode: TileCoverMode,
    open val x: Int,
    open val y: Int
) {
    data class Bomb(
        override val coverMode: TileCoverMode,
        override val x: Int, override val y: Int,
        val userSelection: Boolean = false,
    ) : Tile(coverMode, x, y)

    data class Empty(
        override val coverMode: TileCoverMode,
        override val x: Int,
        override val y: Int,
    ) : Tile(coverMode, x, y)

    data class Adjacent(
        val risk: Int,
        override val coverMode: TileCoverMode,
        override val x: Int,
        override val y: Int,

        ) : Tile(coverMode, x, y)
}
