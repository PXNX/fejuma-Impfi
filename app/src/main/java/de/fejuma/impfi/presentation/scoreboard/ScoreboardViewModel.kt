package de.fejuma.impfi.presentation.scoreboard

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.difficulties
import de.fejuma.impfi.model.Highscore
import kotlinx.coroutines.launch
import javax.inject.Inject

//inject the dependencies in the constructor, find repository in the modules an inject it
@HiltViewModel
class ScoreboardViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    // Executed on initialization of the ViewModel, as we want the Highscores to bew present directly
    init {
        loadHighscores()
    }

    var highscores = mutableStateListOf<List<Highscore>?>(null, null, null)
        private set

    // Getting all Highscores at once and not when one list when navigating to another page in our
    // viewPager allows for smoother page changes. So we don't have to navigate there, then wait
    // for it to load - but because it's not that much data, why not preload the other pages?
    private fun loadHighscores() {
        difficulties.keys.forEachIndexed { index, level ->
            viewModelScope.launch {
                repo.getHighscoresByDifficulty(level).collect {
                    highscores[index] = it
                }
            }
        }
    }
}