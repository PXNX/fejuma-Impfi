package de.fejuma.impfi.presentation.game.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.fejuma.impfi.presentation.game.game.Status
import de.fejuma.impfi.presentation.game.game.Tile
import de.fejuma.impfi.presentation.game.game.TileCoverMode

@Composable
fun MainView(
    time: Int,
    minesRemaining: Int,
    map: List<List<Tile>>,
    status: Status,
    onTileSelected: (column: Int, row: Int) -> Unit,
    onTileSelectedSecondary: (column: Int, row: Int) -> Unit,
    onRestartSelected: () -> Unit,
) {
    Column {
        //  GameBar(time, minesRemaining, status, onRestartSelected)
        GameMap(map, onTileSelected, onTileSelectedSecondary)
    }
}

@Preview
@Composable
fun AppPreview() {
    MainView(20, 5, listOf(
        listOf(
            Tile.Empty(TileCoverMode.COVERED, 0, 0),
            Tile.Empty(TileCoverMode.UNCOVERED, 0, 1),
            Tile.Empty(TileCoverMode.COVERED, 0, 2)
        ),
        listOf(
            Tile.Bomb(TileCoverMode.COVERED, 1, 0),
            Tile.Bomb(TileCoverMode.FLAGGED, 1, 1),
            Tile.Bomb(TileCoverMode.COVERED, 1, 2)
        ),
        listOf(
            Tile.Adjacent(1, TileCoverMode.UNCOVERED, 2, 0),
            Tile.Adjacent(2, TileCoverMode.UNCOVERED, 2, 1),
            Tile.Adjacent(3, TileCoverMode.COVERED, 2, 1)
        ),
    ), Status.WON, { _, _ -> }, { _, _ -> }, {})
}