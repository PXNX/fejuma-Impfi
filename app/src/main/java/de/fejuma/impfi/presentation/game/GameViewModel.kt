package de.fejuma.impfi.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.model.difficulties
import de.fejuma.impfi.presentation.game.game.Game
import de.fejuma.impfi.presentation.game.game.GameStateHolder
import de.fejuma.impfi.presentation.game.game.MutableGameStateHolder
import de.fejuma.impfi.presentation.game.game.Status
import de.fejuma.impfi.presentation.game.game.Tile
import de.fejuma.impfi.presentation.game.game.TileCoverMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {


  val difficulty = difficulties[repo.getDifficulty()]!!
  /*  val game = Game()






    init {

        game.configure(
            columns = difficulty.width,
            rows = difficulty.height,
            mines =difficulty.mines,
        )
    }

   */

}

