package de.fejuma.impfi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO to datasource???
@Entity(tableName = "scores")
data class Highscore(


    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "difficulty")
    var difficulty: DifficultyLevel,

    @ColumnInfo(name = "seconds")
    val seconds: Int,

    @ColumnInfo(name = "hints")
    val hintsUsed: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
)