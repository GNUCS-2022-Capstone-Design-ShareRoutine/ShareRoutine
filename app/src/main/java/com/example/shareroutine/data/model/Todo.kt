package com.example.shareroutine.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

    //@Entity(tableName = "Todo", inheritSuperIndices = true)
    @Entity(
        tableName = "Todo",
        foreignKeys = arrayOf(
            ForeignKey(
                entity = Routine::class,
                parentColumns = arrayOf("uid"),
                childColumns = arrayOf("routine_uid"),
                onDelete = ForeignKey.CASCADE
            )
        )
    )
    data class Todo(
        @PrimaryKey val uid: Int,
        @ColumnInfo(name = "todo_routine_uid") var routine_uId: Int,
        @ColumnInfo(name = "todo_time") var time: Float,
        @ColumnInfo(name = "todo_timeInfo") var timeInfo: Int,
        @ColumnInfo(name = "todo_content") var content: String,
        @ColumnInfo(name = "todo_clear") var clear: Boolean
    )