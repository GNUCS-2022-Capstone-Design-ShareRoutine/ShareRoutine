package com.example.shareroutine.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shareroutine.data.model.Routine

@Database(entities = [Routine::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
}