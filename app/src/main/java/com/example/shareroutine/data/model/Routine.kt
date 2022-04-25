package com.example.shareroutine.data.model

import androidx.room.*

// Temporary model
@Entity(
    tableName = "routine",
    indices = arrayOf(
        Index(
            value = ["routine_title", "routine_term", "routine_username"],
            unique = true
        )
    )
)
data class Routine (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "routine_title") var title: String,
    @ColumnInfo(name = "routine_term") var term: String,
    @ColumnInfo(name = "routine_username") var username: String
)


