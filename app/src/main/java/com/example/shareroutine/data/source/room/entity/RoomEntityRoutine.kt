package com.example.shareroutine.data.source.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "routine_table",
    indices = [Index(value = ["name"], unique = true)]
)
data class RoomEntityRoutine(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var term: Int,
    var isUsed: Boolean,
    var userId: String
)
