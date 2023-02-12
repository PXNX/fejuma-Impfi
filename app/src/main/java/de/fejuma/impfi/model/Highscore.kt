package de.fejuma.impfi.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Highscore(


    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "difficulty")
    var difficulty: DifficultyLevel,

    @ColumnInfo(name = "seconds")
    val seconds: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
)