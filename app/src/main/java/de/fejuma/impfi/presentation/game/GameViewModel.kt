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
import de.fejuma.impfi.presentation.game.game.GameStateHolder
import de.fejuma.impfi.presentation.game.game.MutableGameStateHolder
import de.fejuma.impfi.presentation.game.game.Status
import de.fejuma.impfi.presentation.game.game.Tile
import de.fejuma.impfi.presentation.game.game.TileCoverMode
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


    val difficulty = difficulties[repo.getDifficulty()]!!

    var sfxVolume by mutableIntStateOf(repo.getSfxVolume())
        private set
    var hapticsEnabled by mutableStateOf(repo.getHapticsEnabled())
        private set

    var recordTime: Int? = null

    var isSurrenderDialog by mutableStateOf(false)
        private set
    var isTimerActive by mutableStateOf(true)


    fun setOpenSurrenderDialog(isOpen: Boolean) {
        isTimerActive = !isOpen
        isSurrenderDialog = isOpen
    }

    init {
        viewModelScope.launch {


            val scores = repo.getHighscoresByDifficulty(repo.getDifficulty()).first()

            if (scores.isNotEmpty()) {
                recordTime = scores[0].seconds
            }


        }

        configure(
            difficulty.width,
            difficulty.height,
            difficulty.mines
        )
    }

    fun saveHighScore(highScore: Highscore) {
        viewModelScope.launch {
            repo.insertHighscore(highScore)
        }
    }


    private lateinit var random: Random


    private val _map: MutableList<MutableList<Tile>> = mutableListOf()
    private val _statusHolder = MutableGameStateHolder(
        MutableStateFlow(0),
        MutableStateFlow(0),
        MutableStateFlow(emptyList()),
        MutableStateFlow(Status.NORMAL)

    )
    val gameStateHolder: GameStateHolder = _statusHolder


    suspend fun timeFlow() = flow {
        for (i in 0..timeLimit) {


            while (!isTimerActive) {
                delay(1_000)
            }
            emit(i)
            delay(1_000)


        }
    }
        .takeWhile { isGameRunning }.collect {
            _statusHolder.time.value = it
        }


    private var timerJob: Job? = null
    private var isGameRunning = false

    private var columns: Int = 0
    private var rows: Int = 0
    private var mines: Int = 0
    private var firstSelection = true

    fun configure(
        columns: Int = 15,
        rows: Int = 15,
        mines: Int = 30,
    ) {
        this.columns = columns
        this.rows = rows
        this.mines = mines

        this.random = Random

        firstSelection = true
        isGameRunning = false


        _map.clear()
        repeat(rows) { y ->
            val row = mutableListOf<Tile>()
            repeat(columns) { x ->
                row.add(Tile.Empty(TileCoverMode.COVERED, x, y))
            }
            _map.add(row)
        }
        _statusHolder.minesRemaining.value = mines
        _statusHolder.status.value = Status.NORMAL
        _statusHolder.time.value = 0
        updateMapState()
    }


    private fun initializeMap(initialColumn: Int, initialRow: Int) {
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

        repeat(rows) { row ->
            repeat(columns) { column ->
                updateRiskFactor(column, row)
            }
        }

        firstSelection = false
        isGameRunning = true



        viewModelScope.launch {
            timeFlow()
        }


    }

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

    private fun winGame() {
        isGameRunning = false
        timerJob?.cancel()
        _statusHolder.status.value = Status.WON
        _statusHolder.minesRemaining.value = 0
    }

    private fun uncoverTile(column: Int, row: Int) {
        val currentTile = _map.getOrNull(row)?.getOrNull(column) ?: return

        if (currentTile.coverMode == TileCoverMode.UNCOVERED) {
            return
        }

        _map[row][column] = when (currentTile) {
            is Tile.Bomb -> return
            is Tile.Adjacent -> Tile.Adjacent(
                currentTile.risk,
                TileCoverMode.UNCOVERED,
                column,
                row
            )

            is Tile.Empty -> Tile.Empty(TileCoverMode.UNCOVERED, column, row)
        }

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

    fun primaryAction(column: Int, row: Int) {
        if (firstSelection) {
            initializeMap(column, row)
        }

        if (!isGameRunning) {
            return
        }

        when (_map[row][column]) {
            is Tile.Bomb -> loseGame(column, row)
            is Tile.Adjacent, is Tile.Empty -> uncoverTile(column, row)
        }

        var remainingTiles = 0
        repeat(rows) { row ->
            repeat(columns) { column ->
                if (_map[row][column].coverMode != TileCoverMode.UNCOVERED) {
                    remainingTiles++
                }
            }
        }

        updateMapState()

        if (remainingTiles == mines) {
            winGame()
        }
    }

    fun secondaryAction(column: Int, row: Int) {
        if (!isGameRunning) {
            return
        }

        val tile = _map[row][column]
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

        _map[row][column] = when (tile) {
            is Tile.Adjacent -> tile.copy(coverMode = nextCoverMode)
            is Tile.Bomb -> tile.copy(coverMode = nextCoverMode)
            is Tile.Empty -> tile.copy(coverMode = nextCoverMode)
        }
        updateMapState()
    }

    private fun updateMapState() {
        _statusHolder.map.value = _map.map {
            it.toList()
        }
    }


}

