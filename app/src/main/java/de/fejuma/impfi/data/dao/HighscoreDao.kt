package de.fejuma.impfi.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore

@Dao
interface HighScoreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHighScore(HighScore: Highscore)

    @Query("SELECT * FROM scores WHERE difficulty = :difficultyLevel")
    fun findHighScoreByDifficultyLevel(difficultyLevel: DifficultyLevel): Highscore

    @Query("SELECT * FROM scores")
    fun getAllHighScores(): List<Highscore>

    @Update
    suspend fun updateHighScoreDetails(HighScore: Highscore)

    @Delete
    suspend fun deleteHighScore(HighScore: Highscore)
}