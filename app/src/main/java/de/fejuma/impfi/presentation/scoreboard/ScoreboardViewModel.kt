package de.fejuma.impfi.presentation.scoreboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.model.Difficulty
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import de.fejuma.impfi.model.difficulties
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreboardViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {


    private val _highscores = mutableStateOf<List<Highscore>?>(emptyList())
    val highscores: State<List<Highscore>?>
        get() = _highscores

    private val _selectedIndex = mutableStateOf(0)
    val selectedIndex: State<Int>
        get() = _selectedIndex


    fun setSelectedIndex(difficulty: Difficulty) {
        getHighscores(difficulty.level)
        _selectedIndex.value = difficulties.indexOf(difficulty)
    }


    private fun getHighscores(difficultyLevel: DifficultyLevel) {
        viewModelScope.launch {
            val result = repo.getHighscoresByDifficulty(difficultyLevel)
            result.collect {
                _highscores.value = it
            }
        }

    }


    fun createEntries() {
        viewModelScope.launch {
            repo.insertHighscore(
                Highscore(
                    listOf("Peter", "AAAAAA", "TESTCHT").random(),
                    DifficultyLevel.values().random(),
                    (10..10000 step 25).toList().random()
                )
            )
        }

    }
}