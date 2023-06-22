package de.fejuma.impfi.presentation.game.model

// You can't create instances of a sealed class that are not contained within here already, so
// there's not just a Tile, but it has to be a Bomb, Empty or Adjacent.
sealed class Tile(
    open var coverMode: TileCoverMode,
    open val x: Int,
    open val y: Int
) {
    data class Bomb(
        override var coverMode: TileCoverMode,
        override val x: Int,
        override val y: Int,
        val userSelection: Boolean = false,
    ) : Tile(coverMode, x, y)

    data class Empty(
        override var coverMode: TileCoverMode,
        override val x: Int,
        override val y: Int,
    ) : Tile(coverMode, x, y)

    data class Adjacent(
        val risk: Int,
        override var coverMode: TileCoverMode,
        override val x: Int,
        override val y: Int,
    ) : Tile(coverMode, x, y)
}

enum class TileCoverMode {
    COVERED,
    FLAGGED,
    QUESTIONED,
    UNCOVERED,
}
