package com.example.shareroutine.data.source.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

// onUpdate
@Entity(
    tableName = "todo_table",
    foreignKeys = [ForeignKey(entity = RoomEntityRoutine::class, parentColumns = ["id"], childColumns = ["routineId"], onDelete = CASCADE)],
    indices = [Index(value = ["routineId"])]
)
data class RoomEntityTodo(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var dateTime: ZonedDateTime,
    var importance: Int,
    var description: String,
    var achieved: Boolean,
    var routineId: Int? = null
)