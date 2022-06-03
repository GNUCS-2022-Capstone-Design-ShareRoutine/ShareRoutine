package com.example.shareroutine.data.source.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.Month

// onUpdate
@Entity(
    tableName = "todo_table",
    foreignKeys = [ForeignKey(entity = RoomEntityRoutine::class, parentColumns = ["id"], childColumns = ["routineId"], onDelete = CASCADE)],
    indices = [Index(value = ["routineId"])]
)
data class RoomEntityTodo(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var time: LocalTime? = null,
    var dayOfWeek: DayOfWeek? = null,
    var day: Int? = null,
    var month: Month? = null,
    var importance: Int,
    var description: String,
    var routineId: Int? = null
)
