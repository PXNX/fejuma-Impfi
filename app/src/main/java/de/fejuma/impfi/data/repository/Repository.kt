package de.fejuma.impfi.data.repository

import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun setSfxVolume(volume: Int): Boolean
    fun getSfxVolume(): Int

    fun setDifficulty(difficultyLevel: DifficultyLevel): Boolean
    fun getDifficulty(): DifficultyLevel

    fun getHighscores(): Flow<List<Highscore>>
    suspend fun getHighscoresByDifficulty(difficultyLevel: DifficultyLevel): Flow<List<Highscore>>
    suspend fun insertHighscore(highscore: Highscore)
    suspend fun deleteHighscore(highscore: Highscore)
}