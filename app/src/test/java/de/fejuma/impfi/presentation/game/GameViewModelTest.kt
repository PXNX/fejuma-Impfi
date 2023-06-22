package de.fejuma.impfi.presentation.game

import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.presentation.game.model.Status
import de.fejuma.impfi.presentation.game.model.Tile
import de.fejuma.impfi.presentation.game.model.TileCoverMode
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel

    val rows = 4
    val columns = 3
    val mines = 5

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


        assertEquals(Status.NORMAL, result.status.value)
        assertEquals(mines, result.minesRemaining.value)

        assertEquals(expected.size, result.map.value.size)
        assertEquals(expected[0][0].x, result.map.value[0][0].x)
    }


    @Test
    fun initializeMap() = runTest {
        viewModel.configure(columns, rows, mines)
        viewModel.callPrivateFunc("initializeMap", 1, 3)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            //    viewModel.callPrivateFunc("timeFlow",)

        }

        delay(8_000)


        assertEquals(7, viewModel.gameStateHolder.time.value)

        assertEquals(true, viewModel.getPrivateProperty("isGameRunning"))

    }

}

inline fun <reified T> T.callPrivateFunc(name: String, vararg args: Any?): Any? =
    T::class
        .declaredMemberFunctions
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.call(this, *args)

inline fun <reified T : Any, R> T.getPrivateProperty(name: String): R? =
    T::class
        .memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as? R