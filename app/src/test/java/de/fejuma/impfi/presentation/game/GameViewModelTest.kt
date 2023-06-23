package de.fejuma.impfi.presentation.game

import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.presentation.game.model.Status
import de.fejuma.impfi.presentation.game.model.Tile
import de.fejuma.impfi.presentation.game.model.TileCoverMode
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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


// Contains common use cases where a test may be useful
@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel

    private val rows = 4
    private val columns = 3
    private val mines = 5

    // Do this before every test runs
    @Before
    fun setUp() {
        //Important if we want to use Coroutines as those need a Context in which to be executed
        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel = GameViewModel(RepositoryMock)
    }

    // Do this after each test runs
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    // Setting up the game field, then checking whether it matches what we expect it to look like
    @Test
    fun configure() {
        // Preparing our expected result
        val expected = mutableListOf<List<Tile>>()
        repeat(rows) { y ->
            val row = mutableListOf<Tile>()
            repeat(columns) { x ->
                row.add(Tile.Empty(TileCoverMode.COVERED, x, y))
            }
            expected.add(row)
        }

        // Calling the method we want to test, saving the result
        viewModel.configure(columns, rows, mines)
        val result = viewModel.gameStateHolder

        // Checking whether the result matches what we expected the called method to do
        assertEquals(Status.NORMAL, result.status.value)
        assertEquals(mines, result.minesRemaining.value)

        assertEquals(expected.size, result.map.value.size)
        assertEquals(expected[0][0].x, result.map.value[0][0].x)
    }


    // This test includes running the Coroutine timeFlow and accessing private methods and
    // properties using reflection
    @Test
    fun initializeMap() = runTest(UnconfinedTestDispatcher()) {

        // First set up the game field, as that's what we need later on. Usually you should mock
        // calls outside a unit though. As libs like mockito don't belong to Google anymore, this
        // was not an option for us, so we'll skip this part.
        viewModel.configure(columns, rows, mines)

        // Reflection allows you to find a classes methods via name, even if their visibility is
        // private.  We then make is accessible to us (because this one is private), then call it
        // with an asterisk in front of the arguments. This is a spread operator, which in this
        // case is required for varargs
        viewModel::class
            .declaredMemberFunctions
            .firstOrNull { it.name == "initializeMap" }
            ?.apply { isAccessible = true }
            ?.call(viewModel, *arrayOf(1, 3))

        // As the time flow emits values every second, let's wait 5 seconds so that it can complete
        // 4 of those cycles
        delay(5_000)

        // Given that it starts at 0 and delays after emitting a value, we can now assume the it
        // should have counted up to 4
        assertEquals(4, viewModel.gameStateHolder.time.value)

        // Much like a private method, reflection also allows access to properties
        val isGameRunning = viewModel::class
            .memberProperties
            .firstOrNull { it.name == "isGameRunning" }
            ?.apply { isAccessible = true }
            ?.getter
            ?.call(viewModel)

        assertEquals(true, isGameRunning)
    }
}