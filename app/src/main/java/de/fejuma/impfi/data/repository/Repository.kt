package de.fejuma.impfi.data.repository

import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import kotlinx.coroutines.flow.Flow

// A repository serves as the single source of truth of an application, meaning that it integrates
// the various data sources we use, so that we don't have to look in multiple places where our data
// comes from
interface Repository {

    fun setSfxVolume(volume: Int): Boolean
    fun getSfxVolume(): Int

    fun setHapticsEnabled(isEnabled: Boolean): Boolean
    fun getHapticsEnabled(): Boolean

    fun setDifficulty(difficultyLevel: DifficultyLevel): Boolean
    fun getDifficulty(): DifficultyLevel

    fun getHighscores(): Flow<List<Highscore>>
    suspend fun getHighscoresByDifficulty(difficultyLevel: DifficultyLevel): Flow<List<Highscore>>
    suspend fun insertHighscore(highscore: Highscore)
    suspend fun deleteHighscore(highscore: Highscore)
}