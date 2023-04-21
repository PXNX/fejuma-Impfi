package de.fejuma.impfi.presentation.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.model.Cell
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    //instead of using lots of separate states, it may be better to go for dataclass -- less bloat


    var board by mutableStateOf<Array<Array<Cell>>>(emptyArray())
        private set

    private val mineCount = 10

    private lateinit var minefield: Array<Array<Cell>>
    private var width by Delegates.notNull<Int>()
    private var height by Delegates.notNull<Int>()

    init {
        startGame(6, 8, 10)
    }

    fun newGame() {
        // startGame(10, 19, 20)
    }

    fun startGame(height: Int, width: Int, mineAmount: Int) {
        minefield = Array(width) { Array(height) { Cell() } }
        this.width = width
        this.height = height

        var noOfMinesPlaced = 0
        while (noOfMinesPlaced < mineAmount) {
            val row = Random.nextInt(width)
            val col = Random.nextInt(height)
            if (!minefield[row][col].isMine) {
                minefield[row][col].isMine = true
                increaseAllNeighbors(row, col)
                noOfMinesPlaced++
            }
        }




        Log.d("GameVM", "New Board: $minefield")
        board = minefield
    }

    /*   private fun checkWin(): Boolean {
           return ((board.value.flatten().count { !it.isMine } == mineCount) or board.value.flatten()
               .filter { it.isMine }.all { it.isFlagged })
           //  && (winState != EndState.LOST)
       }

     */


    // we know 'row'/'col' indicates a mine, so all its neighbors
// are increased by 1 (if they are not a mine)
    private fun increaseAllNeighbors(row: Int, col: Int) {
        for (x in (row - 1)..(row + 1)) {
            for (y in (col - 1)..(col + 1)) {
                if (isWithinBoundary(x, y)) {
                    if (minefield[x][y].isMine) {
                        continue
                    }
                    minefield[x][y].nearbyMines++
                }
            }
        }
    }


    private fun isWithinBoundary(x: Int, y: Int) = x in 0 until width && y in 0 until height

}

