package com.example.shareroutine.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


// Temporary model
@Entity(
    tableName = "user",
    indices = arrayOf(
        Index(
            value = ["user_nickname", "user_snsId"],
            unique = true
        )
    )
)
data class User (
    @PrimaryKey(autoGenerate = true) val userId: String,
    @ColumnInfo(name = "user_nickname") var nickname: String,
    @ColumnInfo(name = "user_snsId") var snsId: String,
)