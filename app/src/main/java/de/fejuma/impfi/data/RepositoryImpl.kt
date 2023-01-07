package de.fejuma.impfi.data

import de.fejuma.impfi.data.local.PrefSource
import de.fejuma.impfi.model.DifficultyLevel

class RepositoryImpl(private val prefSource: PrefSource, ) : Repository { //roomSource:

    public fun setSfxVolume(volume:Int) {
        prefSource.setSfxVolume(volume)
    }
    public fun getSfxVolume() = prefSource.getSfxVolume()

    public fun setDifficulty(difficultyLevel: DifficultyLevel) {
        prefSource.setDifficulty(difficultyLevel)
    }
    public fun getDifficulty() = prefSource.getDifficulty()


}