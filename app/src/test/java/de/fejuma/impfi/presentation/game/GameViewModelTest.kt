package de.fejuma.impfi.presentation.game

import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.presentation.game.model.Status
import de.fejuma.impfi.presentation.game.model.Tile
import de.fejuma.impfi.presentation.game.model.TileCoverMode
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = GameViewModel(RepositoryMock)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun configure() {
        val rows = 4
        val columns = 3
        val mines = 5

        val expected = mutableListOf<List<Tile>>()

        repeat(rows) { y ->
            val row = mutableListOf<Tile>()
            repeat(columns) { x ->
                row.add(Tile.Empty(TileCoverMode.COVERED, x, y))
            }
            expected.add(row)
        }


        viewModel.configure(columns, rows, mines)

        val result = viewModel.gameStateHolder


        assertEquals(result.status.value, Status.NORMAL)
        assertEquals(result.minesRemaining.value, mines)

        assertEquals(result.map.value.size, expected.size)
        assertEquals(result.map.value[0][0].x, expected[0][0].x)
    }
}