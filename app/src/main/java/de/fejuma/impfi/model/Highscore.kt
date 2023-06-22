package de.fejuma.impfi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// This dataclass is special as it defines the columns for when it's saved as an entity in the
// Database
@Entity(tableName = "scores")
data class Highscore(


    @ColumnInfo(name = "username")
    val username: String,

    // In a database we can not only save primitives, but also e.g. enums
    @ColumnInfo(name = "difficulty")
    var difficulty: DifficultyLevel,

    @ColumnInfo(name = "seconds")
    val seconds: Int,

    @ColumnInfo(name = "hints")
    val hintsUsed: Int,

    // The default value will be overridden when saved to the database. It's just so that we don't
    // get the idea to set it somewhere in the code.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
)