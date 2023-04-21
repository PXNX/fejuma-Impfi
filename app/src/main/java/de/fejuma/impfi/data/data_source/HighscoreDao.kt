package de.fejuma.impfi.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import kotlinx.coroutines.flow.Flow

//Data Access Object; SQL-Statements zur DB-Ansprache
// Kategorisieren der DAOs nach Table oder Feature
@Dao
interface HighscoreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHighScore(highScore: Highscore)

    @Query("SELECT * FROM scores WHERE difficulty = :difficultyLevel ORDER BY seconds  LIMIT 20;")
    fun findHighScoresByDifficultyLevel(difficultyLevel: DifficultyLevel): Flow<List<Highscore>>

    @Query("SELECT * FROM scores")
    fun getAllHighScores(): Flow<List<Highscore>>

    @Update
    suspend fun updateHighScoreDetails(highScore: Highscore)

    @Delete
    suspend fun deleteHighScore(highScore: Highscore)
}