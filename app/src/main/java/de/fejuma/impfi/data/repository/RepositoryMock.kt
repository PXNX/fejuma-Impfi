package de.fejuma.impfi.data.repository

import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object RepositoryMock : Repository {


    override fun setSfxVolume(volume: Int): Boolean {
        return true
    }

    override fun getSfxVolume() = 50

    override fun setDifficulty(difficultyLevel: DifficultyLevel): Boolean {
        return true
    }

    override fun getHapticsEnabled() = true

    override fun setHapticsEnabled(isEnabled: Boolean): Boolean {
        return true
    }

    override fun getDifficulty(): DifficultyLevel =
        DifficultyLevel.valueOf(DifficultyLevel.NORMAL.toString())

    override fun getHighscores(): Flow<List<Highscore>> {
        return flowOf(
            listOf(
                Highscore("Felix", DifficultyLevel.EASY, 120, 4),
                Highscore("Julian", DifficultyLevel.EASY, 180, 500),
                Highscore("Max", DifficultyLevel.NORMAL, 6000, 2)
            )
        )
    }

    override suspend fun getHighscoresByDifficulty(difficultyLevel: DifficultyLevel): Flow<List<Highscore>> =
        flowOf(
            listOf(
                Highscore("Paul", DifficultyLevel.EASY, 41, 0),
                Highscore("Julian", DifficultyLevel.EASY, 180, 12),
                Highscore("Max", DifficultyLevel.EASY, 189, 2),
                Highscore("Felix", DifficultyLevel.EASY, 12000, 0),
                Highscore("Julian", DifficultyLevel.NORMAL, 17001, 50000),
            )
        )

    override suspend fun insertHighscore(highscore: Highscore) {

    }

    override suspend fun deleteHighscore(highscore: Highscore) {

    }
}