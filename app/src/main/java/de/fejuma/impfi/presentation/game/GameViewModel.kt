package de.fejuma.impfi.presentation.game

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.model.Highscore
import de.fejuma.impfi.model.TileState
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    //instead of using lots of separate states, it may be better to go for dataclass -- less bloat

    private val _board =  mutableStateOf(emptyList<List<TileState>>())
    val board: MutableState<List<List<TileState>>>
        get() = _board

    private val mineCount =  10

    init{
        startGame(6,8, 10)
    }

    fun newGame(){
        startGame(10,19,20)
    }

    fun startGame(height:Int,width:Int, mines:Int){

        val newBoard =  List(height) { x -> List(width) { y-> TileState(x,y) } }

        val cords = mutableListOf<Pair<Int,Int>>()

        for (x in 0 until height){
            for (y in 0 until width){
                cords.add(Pair(x,y))
            }
        }
        cords.shuffle()

        (0 until mines).forEach{i->

            newBoard[cords[i].first][cords[i].second].isMine = true
        }

        Log.d("GameVM", "New Board: $newBoard")
        _board.value = newBoard
    }

    private fun checkWin(): Boolean {
        return ((board.value.flatten().count { !it.isMine } == mineCount) or board.value.flatten().filter { it.isMine }.all { it.isFlagged })
              //  && (winState != EndState.LOST)
    }

    
}