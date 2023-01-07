package de.fejuma.impfi.data.data_source


import androidx.room.Database
import androidx.room.RoomDatabase
import de.fejuma.impfi.model.Highscore

@Database(
    entities = [Highscore::class],
    version = 1
)
abstract class HighscoreDatabase : RoomDatabase() {

    abstract val highScoreDao: HighscoreDao

    companion object {
        const val DATABASE_NAME = "highscore_db"
    }
}