package de.fejuma.impfi.presentation.game

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
        startGame(6,8)
    }

    fun startGame(height:Int,width:Int){
        _board.value =  List(height) { x -> List(width) { y-> TileState(x,y) } }
    }

    private fun checkWin(): Boolean {
        return ((board.value.flatten().count { !it.isMine } == mineCount) or board.value.flatten().filter { it.isMine }.all { it.isFlagged })
              //  && (winState != EndState.LOST)
    }
}