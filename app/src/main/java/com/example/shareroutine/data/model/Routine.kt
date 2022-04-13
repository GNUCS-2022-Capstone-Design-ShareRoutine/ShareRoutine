package com.example.shareroutine.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Temporary model
//@Entity(tableName = "routine", inheritSuperIndices = true)
@Entity(
    tableName = "user",
    indices = arrayOf(
        Index(
            value = ["routine_title", "routine_term"],
            unique = true
        )
    )
)
data class Routine(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "routine_title") var title: String,
    @ColumnInfo(name = "routine_term") var term: String
)



