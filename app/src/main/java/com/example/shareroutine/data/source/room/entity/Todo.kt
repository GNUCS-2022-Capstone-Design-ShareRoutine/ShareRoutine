package com.example.shareroutine.data.source.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(
    tableName = "todo_table",
    foreignKeys = [ForeignKey(Routine::class, parentColumns = ["id"], childColumns = ["routineId"])]
)
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var dateTime: ZonedDateTime,
    var importance: Int,
    var description: String,
    var achieved: Boolean,
    var routineId: Int
)
