package de.fejuma.impfi.presentation.scoreboard

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.difficulties
import de.fejuma.impfi.model.Highscore
import kotlinx.coroutines.launch
import javax.inject.Inject

//inject the dependencies in the constructor
//find repository in the modules an inject it
@HiltViewModel
class ScoreboardViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {
    //TODO: use some actual result sealed class here, that allows for loading states with circularprogressindicator later on

    init {
        loadHighscores()
    }

    var highscores = mutableStateListOf<List<Highscore>?>(null, null, null)
        private set

    private fun loadHighscores() {
        difficulties.keys.forEachIndexed { index, level ->
            viewModelScope.launch {
                Log.e("SCO_VM", "index: $index - level: $level")
                repo.getHighscoresByDifficulty(level).collect {
                    Log.e("SCO_VM", "index: $index --- it: $it")
                    highscores[index] = it
                }
            }
        }
    }
}