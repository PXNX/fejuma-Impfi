package de.fejuma.impfi.data.repository

import android.content.Context
import android.content.SharedPreferences
import de.fejuma.impfi.data.data_source.DIFFICULTY
import de.fejuma.impfi.data.data_source.HAPTICS_ENABLED
import de.fejuma.impfi.data.data_source.HighscoreDao
import de.fejuma.impfi.data.data_source.SFX_VOLUME
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import kotlinx.coroutines.flow.Flow

// Contains calls to the actual data sources
class RepositoryImpl(
    context: Context,
    private val highscoreDao: HighscoreDao
) : Repository {

    // Shared preferences is basically some xml-File within our App's directory which allows us to
    // save data as simple key-value pairs, e.g. Boolean, Int and String
    private val pref: SharedPreferences =
        context.getSharedPreferences("ImpfiPref", Context.MODE_PRIVATE)
    private val editor = pref.edit()

    // After setting a value to a specific key we have to commit our changes for them to be
    // persisted. If this was successful, we return True
    override fun setSfxVolume(volume: Int): Boolean {
        editor.putInt(SFX_VOLUME, volume)
        return editor.commit()
    }

    // Reading the data also requires a default value in case a key is not yet present. The = is a
    // shortcut for a function that just returns a single line
    override fun getSfxVolume() = pref.getInt(SFX_VOLUME, 50)

    override fun setHapticsEnabled(isEnabled: Boolean): Boolean {
        editor.putBoolean(HAPTICS_ENABLED, isEnabled)
        return editor.commit()
    }

    override fun getHapticsEnabled(): Boolean = pref.getBoolean(HAPTICS_ENABLED, true)

    // As we can only save primitives and Strings, we have to convert the Difficulty enum to String
    override fun setDifficulty(difficultyLevel: DifficultyLevel): Boolean {
        editor.putString(DIFFICULTY, difficultyLevel.toString())
        return editor.commit()
    }

    // And also convert it back to the enum from String when reading data
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