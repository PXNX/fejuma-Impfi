package de.fejuma.impfi.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.model.Highscore
import de.fejuma.impfi.model.difficulties
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {


    val difficulty = difficulties[repo.getDifficulty()]!!

    fun saveHighscore(highscore: Highscore) {
        viewModelScope.launch {
            repo.insertHighscore(highscore)
        }
    }
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

