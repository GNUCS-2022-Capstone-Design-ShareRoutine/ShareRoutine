package com.example.shareroutine.data.source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine_table")
data class Routine(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var term: Int
)
