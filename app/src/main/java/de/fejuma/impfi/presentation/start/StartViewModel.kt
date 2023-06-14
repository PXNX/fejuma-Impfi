package de.fejuma.impfi.presentation.start

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.model.DifficultyLevel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    var sfxVolume by mutableIntStateOf(repo.getSfxVolume())
        private set

    var difficulty by mutableStateOf(repo.getDifficulty())
        private set

    fun changeSfxVolume(sfxVolume: Int) {
        val result = repo.setSfxVolume(sfxVolume)

        if (result) {
            this.sfxVolume = sfxVolume
        }
    }


    fun changeDifficulty(difficultyLevel: DifficultyLevel) {
        val result = repo.setDifficulty(difficultyLevel)

        if (result) {
            this.difficulty = difficultyLevel
        }
    }
}