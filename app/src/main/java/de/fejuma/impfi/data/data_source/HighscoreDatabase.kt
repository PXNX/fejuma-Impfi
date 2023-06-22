package de.fejuma.impfi.data.data_source


import androidx.room.Database
import androidx.room.RoomDatabase
import de.fejuma.impfi.model.Highscore

// Abstracts away the creation of a Room DB that has our entities' fields as columns. If you make
// changes to the any of the models this is based upon, you have to increment the version as it may
// otherwise conflict with different schemas using the same version.
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