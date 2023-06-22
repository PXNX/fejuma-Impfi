package de.fejuma.impfi.presentation.start

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.fejuma.impfi.data.repository.Repository
import de.fejuma.impfi.model.DifficultyLevel
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class StartViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    //volume for sound effects
    var sfxVolume by mutableFloatStateOf(repo.getSfxVolume().toFloat())
        private set
    var hapticsEnabled by mutableStateOf(repo.getHapticsEnabled())
        private set
    var difficulty by mutableStateOf(repo.getDifficulty())
        private set

    fun changeSfxVolume(sfxVolume: Float) {
        // Set the SFX volume in the repository and get the result
        val result = repo.setSfxVolume(round(sfxVolume).toInt())
        // If the volume change was successful, update the sfxVolume property
        if (result) {
            this.sfxVolume = round(sfxVolume)
        }
    }


    fun changeHapticEnabled(isEnabled: Boolean) {
        val result = repo.setHapticsEnabled(isEnabled)

        if (result) {
            this.hapticsEnabled = isEnabled
        }
    }

    fun changeDifficulty(difficultyLevel: DifficultyLevel) {
        // Set the haptics enabled/disabled status in the repository and get the result
        val result = repo.setDifficulty(difficultyLevel)
        // If the status change was successful, update the hapticsEnabled property
        if (result) {
            this.difficulty = difficultyLevel
        }
    }
}