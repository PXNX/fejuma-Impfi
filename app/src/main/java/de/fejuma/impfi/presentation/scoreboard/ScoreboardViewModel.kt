package de.fejuma.impfi.presentation.scoreboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import kotlinx.coroutines.launch
import javax.inject.Inject

//inject the dependencies in the constructor
//find repository in the modules an inject it
@HiltViewModel
class ScoreboardViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {


    var highscores by mutableStateOf<List<Highscore>>(emptyList())
        private set

    init {
        loadHighscores(repo.getDifficulty())
    }


    fun loadHighscores(difficultyLevel: DifficultyLevel) {
        viewModelScope.launch {

            //TODO: use some actual result sealed class here, that allows for loading states with circularprogressindicator later on
            val result = repo.getHighscoresByDifficulty(difficultyLevel)
            result.collect {
                highscores = it
            }


        }

    }


    fun createEntries() {
        viewModelScope.launch {
            repo.insertHighscore(
                Highscore(
                    listOf("Felix", "Julian", "Max", "Test").random(),
                    DifficultyLevel.EASY,
                    //DifficultyLevel.values().random(),
                    (10..10000 step 250).toList().random()
                )
            )
        }

    }
}

