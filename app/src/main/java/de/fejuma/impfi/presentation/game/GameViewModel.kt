package de.fejuma.impfi.presentation.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.difficulties
import de.fejuma.impfi.model.Highscore
import de.fejuma.impfi.presentation.game.model.GameStateHolder
import de.fejuma.impfi.presentation.game.model.MutableGameStateHolder
import de.fejuma.impfi.presentation.game.model.Status
import de.fejuma.impfi.presentation.game.model.Tile
import de.fejuma.impfi.presentation.game.model.TileCoverMode
import de.fejuma.impfi.timeLimit
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    // Holds the current difficulty level
    val difficultyLevel = repo.getDifficulty()

    // Represents the sound effects volume
    var sfxVolume by mutableIntStateOf(repo.getSfxVolume())
        private set

    // Indicates whether haptics are enabled or disabled
    var hapticsEnabled by mutableStateOf(repo.getHapticsEnabled())
        private set

    // Tracks the number of hints used
    var hintsUsed by mutableIntStateOf(0)
        private set

    // Records the time taken to complete the game
    var recordTime: Int? = null

    // Indicates whether the surrender dialog is open
    var isSurrenderDialog by mutableStateOf(false)
        private set

    // Indicates whether the game timer is active
    var isTimerActive by mutableStateOf(true)


    // Random number generator for game logic
    private lateinit var random: Random

    // Represents the game map
    private val _map: MutableList<MutableList<Tile>> = mutableListOf()

    // Holds the current game state
    private val _statusHolder = MutableGameStateHolder(
        MutableStateFlow(0),
        MutableStateFlow(0),
        MutableStateFlow(emptyList()),
        MutableStateFlow(Status.NORMAL)
    )

    // Exposes the game state holder to observe changes
    val gameStateHolder: GameStateHolder = _statusHolder

    //Job for managing game timer
    private var timerJob: Job? = null

    //Tracks if game is running
    private var isGameRunning = false

    //Game config vars
    private var columns: Int = 0
    private var rows: Int = 0
    private var mines: Int = 0
    private var firstSelection = true

    // Sets the visibility of the surrender dialog and pauses/resumes the game timer accordingly
    fun setOpenSurrenderDialog(isOpen: Boolean) {
        isTimerActive = !isOpen
        isSurrenderDialog = isOpen
    }

    init {
        viewModelScope.launch {

            // Fetch highscores for the current difficulty level
            val scores = repo.getHighscoresByDifficulty(repo.getDifficulty()).first()

            // Set the record time if highscores are available
            if (scores.isNotEmpty()) {
                recordTime = scores[0].seconds
            }


        }

        val difficulty = difficulties[difficultyLevel]!!

        // Configure the game based on the selected difficulty level
        configure(
            difficulty.width,
            difficulty.height,
            difficulty.mines
        )
    }

    // Saves the high score in the repository
    fun saveHighScore(highScore: Highscore) {
        viewModelScope.launch {
            repo.insertHighscore(highScore)
        }
    }

    // Coroutine flow for tracking time during the game. This runs off a background thread, to not
    // make the UI freeze every second. We let it run until a set time limit, so the user can't
    // play until eternity which may break the UI. If the game is paused we check every second if
    // it is resumed.
    private suspend fun timeFlow(start: Int = 0) = flow {
        for (i in start..timeLimit) {

            while (!isTimerActive) {
                delay(1_000)
            }
            emit(i)
            delay(1_000)
        }

        _statusHolder.status.value = Status.LOST
    }
        .takeWhile { isGameRunning }.collect {
            _statusHolder.time.value = it
        }


    fun configure(
        columns: Int = 15,
        rows: Int = 15,
        mines: Int = 30,
    ) {
        // Configures the game with the specified number of columns, rows, and mines.
        // If no values are provided, default values are used (columns = 15, rows = 15, mines = 30)
        this.columns = columns
        this.rows = rows
        this.mines = mines

        this.random = Random

        firstSelection = true
        isGameRunning = false

        // Clears the map and initializes it with empty covered tiles.
        _map.clear()
        repeat(rows) { y ->
            val row = mutableListOf<Tile>()
            repeat(columns) { x ->
                row.add(Tile.Empty(TileCoverMode.COVERED, x, y))
            }
            _map.add(row)
        }
        // Sets the initial values for mines remaining, game status, and time
        _statusHolder.minesRemaining.value = mines
        _statusHolder.status.value = Status.NORMAL
        _statusHolder.time.value = 0
        updateMapState()
    }


    private fun initializeMap(initialColumn: Int, initialRow: Int) {
        // Initializes the map by randomly placing the specified number of mines.
        repeat(mines) {
            var randomX: Int
            var randomY: Int

            while (true) {
                randomX = random.nextInt(columns)
                randomY = random.nextInt(rows)

                val mineAlreadyInPlace = _map[randomY][randomX] is Tile.Bomb
                val isInitialPosition = randomY == initialRow && randomX == initialColumn

                val isPositionAvailable = !mineAlreadyInPlace && !isInitialPosition

                if (isPositionAvailable) {
                    _map[randomY][randomX] = Tile.Bomb(TileCoverMode.COVERED, randomX, randomY)
                    break
                }
            }
        }

        // Calculates and updates the risk factor for each tile on the map.
        repeat(rows) { row ->
            repeat(columns) { column ->
                updateRiskFactor(column, row)
            }
        }

        firstSelection = false
        isGameRunning = true


        //start the game timer
        timerJob = viewModelScope.launch {
            timeFlow()
        }
    }

    // Updates the risk factor for a given tile on the map based on the adjacent bombs
    private fun updateRiskFactor(column: Int, row: Int) {
        val tile = _map[row][column]

        if (tile !is Tile.Empty && tile !is Tile.Adjacent) {
            return
        }

        val adjacentBombs = listOfNotNull(
            _map.getOrNull(row - 1)?.getOrNull(column - 1),
            _map.getOrNull(row - 1)?.getOrNull(column),
            _map.getOrNull(row - 1)?.getOrNull(column + 1),
            _map.getOrNull(row)?.getOrNull(column - 1),
            _map.getOrNull(row)?.getOrNull(column + 1),
            _map.getOrNull(row + 1)?.getOrNull(column - 1),
            _map.getOrNull(row + 1)?.getOrNull(column),
            _map.getOrNull(row + 1)?.getOrNull(column + 1),
        ).count { it is Tile.Bomb }

        if (adjacentBombs > 0) {
            _map[row][column] = Tile.Adjacent(adjacentBombs, TileCoverMode.COVERED, column, row)
        }
    }

    // Handles the game loss scenario.
    private fun loseGame(targetColumn: Int, targetRow: Int) {
        val userSelectionTile = _map[targetRow][targetColumn]
        isGameRunning = false
        timerJob?.cancel()
        repeat(rows) { row ->
            repeat(columns) { column ->
                val bombTile = _map[row][column] as? Tile.Bomb
                if (bombTile != null) {
                    _map[row][column] = bombTile.copy(
                        coverMode = TileCoverMode.UNCOVERED,
                        userSelection = bombTile === userSelectionTile,
                    )
                }
            }
        }
        _statusHolder.status.value = Status.LOST

    }

    //handle game won scenario
    private fun winGame() {
        isGameRunning = false
        timerJob?.cancel()
        _statusHolder.status.value = Status.WON
        _statusHolder.minesRemaining.value = 0
    }

    private fun uncoverTile(column: Int, row: Int) {
        // Get the current tile at the specified position
        val currentTile = _map.getOrNull(row)?.getOrNull(column) ?: return
        // If the current tile is already uncovered, return
        if (currentTile.coverMode == TileCoverMode.UNCOVERED) {
            return
        }

        // Uncover the tile based on its type
        _map[row][column] = when (currentTile) {
            is Tile.Bomb -> return
            is Tile.Adjacent -> Tile.Adjacent(
                currentTile.risk,
                TileCoverMode.UNCOVERED,
                column,
                row
            ) // If it's an adjacent tile, set its cover mode to uncovered

            is Tile.Empty -> Tile.Empty(TileCoverMode.UNCOVERED, column, row)
        }

        // If the current tile is empty, recursively uncover its neighbors
        if (currentTile !is Tile.Empty) {
            return
        }

        uncoverTile(column - 1, row - 1)
        uncoverTile(column - 1, row)
        uncoverTile(column - 1, row + 1)
        uncoverTile(column, row - 1)
        uncoverTile(column, row + 1)
        uncoverTile(column + 1, row - 1)
        uncoverTile(column + 1, row)
        uncoverTile(column + 1, row + 1)
    }

    fun uncoverHintTile() {
        // Find the first covered safe tile with uncovered neighbors
        val hintedTile = _map.asSequence()
            .flatMap { it.asSequence() }
            .firstOrNull { inner ->
                val isSafe = inner !is Tile.Bomb && inner.coverMode == TileCoverMode.COVERED

                if (!isSafe)
                    return@firstOrNull false

                val uncoveredNeighbors = listOfNotNull(
                    _map.getOrNull(inner.x - 1)?.getOrNull(inner.y - 1),
                    _map.getOrNull(inner.x - 1)?.getOrNull(inner.y),
                    _map.getOrNull(inner.x - 1)?.getOrNull(inner.y + 1),
                    _map.getOrNull(inner.x)?.getOrNull(inner.y - 1),
                    _map.getOrNull(inner.x)?.getOrNull(inner.y + 1),
                    _map.getOrNull(inner.x + 1)?.getOrNull(inner.y - 1),
                    _map.getOrNull(inner.x + 1)?.getOrNull(inner.y),
                    _map.getOrNull(inner.x + 1)?.getOrNull(inner.y + 1),
                ).count { it.coverMode == TileCoverMode.UNCOVERED }

                uncoveredNeighbors != 0
            }
            ?: return //if no fitting tile was found, so only mines would remain, don't uncover them

        timerJob?.cancel()

        //time penalty for using hint
        _statusHolder.time.value += 30

        // Restart the timer job with the updated time value
        timerJob = viewModelScope.launch {
            timeFlow(_statusHolder.time.value)
        }

        hintsUsed++

        uncoverTile(hintedTile.x, hintedTile.y)
        updateMapState()
    }

    fun primaryAction(column: Int, row: Int) {
        // If it's the first selection, initialize the map with the specified column and row
        if (firstSelection) {
            initializeMap(column, row)
        }

        if (!isGameRunning) {
            return
        }
        // Check the tile at the specified position
        when (_map[row][column]) {
            is Tile.Bomb -> loseGame(column, row) // If it's a bomb, lose the game
            is Tile.Adjacent, is Tile.Empty -> uncoverTile(
                column,
                row
            ) // If it's an adjacent or empty tile, uncover it
        }

        // Count the number of remaining covered tiles
        var remainingTiles = 0
        repeat(rows) { row ->
            repeat(columns) { column ->
                if (_map[row][column].coverMode != TileCoverMode.UNCOVERED) {
                    remainingTiles++
                }
            }
        }

        updateMapState()

        // If all non-bomb tiles are uncovered, win the game
        if (remainingTiles == mines) {
            winGame()
        }
    }

    fun secondaryAction(column: Int, row: Int) {
        if (!isGameRunning) {
            return
        }

        // Get the tile at the specified position
        val tile = _map[row][column]
        // Determine the next cover mode based on the current cover mode
        val nextCoverMode = when (tile.coverMode) {
            TileCoverMode.COVERED -> TileCoverMode.FLAGGED
            TileCoverMode.FLAGGED -> TileCoverMode.QUESTIONED
            TileCoverMode.QUESTIONED -> TileCoverMode.COVERED
            TileCoverMode.UNCOVERED -> return
        }

        if (nextCoverMode == TileCoverMode.FLAGGED) {
            if (_statusHolder.minesRemaining.value <= 0) {
                return
            }
            _statusHolder.minesRemaining.value--
        } else {
            _statusHolder.minesRemaining.value++
        }

        // Update the cover mode of the tile
        _map[row][column] = when (tile) {
            is Tile.Adjacent -> tile.copy(coverMode = nextCoverMode)
            is Tile.Bomb -> tile.copy(coverMode = nextCoverMode)
            is Tile.Empty -> tile.copy(coverMode = nextCoverMode)
        }
        updateMapState()
    }

    // Update the map state in the status holder
    private fun updateMapState() {
        _statusHolder.map.value = _map.map {
            it.toList()
        }
    }


}

