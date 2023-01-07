package de.fejuma.impfi.presentation.start

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.model.DifficultyLevel
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    private val _sfxVolume = mutableStateOf(getSfxVolume())
    val sfxVolume: State<Int>
        get() = _sfxVolume

    private val _difficulty = mutableStateOf(getDifficulty())
    val difficulty: State<DifficultyLevel>
        get() = _difficulty

    fun setSfxVolume(sfxVolume: Int) {
        val result = repo.setSfxVolume(sfxVolume)

        if (result) {
            _sfxVolume.value = sfxVolume
        }
    }


    private fun getSfxVolume() = repo.getSfxVolume()

    fun setDifficulty(difficultyLevel: DifficultyLevel) {
        val result = repo.setDifficulty(difficultyLevel)

        if (result) {
            _difficulty.value = difficultyLevel
        }

    }

    private fun getDifficulty() = repo.getDifficulty()

}