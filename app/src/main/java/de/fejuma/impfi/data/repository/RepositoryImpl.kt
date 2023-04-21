package de.fejuma.impfi.data.repository

import android.content.Context
import android.content.SharedPreferences
import de.fejuma.impfi.data.data_source.DIFFICULTY
import de.fejuma.impfi.data.data_source.HighscoreDao
import de.fejuma.impfi.data.data_source.SFX_VOLUME
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    context: Context,
    private val highscoreDao: HighscoreDao
) : Repository {

    private val pref: SharedPreferences =
        context.getSharedPreferences("ImpfiPref", Context.MODE_PRIVATE)
    private val editor = pref.edit()

    override fun setSfxVolume(volume: Int): Boolean {
        editor.putInt(SFX_VOLUME, volume)
        return editor.commit()
    }

    override fun getSfxVolume() = pref.getInt(SFX_VOLUME, 50)

    override fun setDifficulty(difficultyLevel: DifficultyLevel): Boolean {
        editor.putString(DIFFICULTY, difficultyLevel.toString())
        return editor.commit()
    }

    override fun getDifficulty(): DifficultyLevel =
        DifficultyLevel.valueOf(pref.getString(DIFFICULTY, DifficultyLevel.NORMAL.toString())!!)

    override fun getHighscores(): Flow<List<Highscore>> {
        return highscoreDao.getAllHighScores()
    }

    override suspend fun getHighscoresByDifficulty(difficultyLevel: DifficultyLevel): Flow<List<Highscore>> =
        highscoreDao.findHighScoresByDifficultyLevel(difficultyLevel)

    override suspend fun insertHighscore(highscore: Highscore) {
        highscoreDao.addHighScore(highscore)
    }

    override suspend fun deleteHighscore(highscore: Highscore) {
        highscoreDao.deleteHighScore(highscore)
    }
}