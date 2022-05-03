package com.example.shareroutine.data.source.room

import androidx.room.*
import com.example.shareroutine.data.source.room.dao.RoutineDao
import com.example.shareroutine.data.source.room.entity.RoomEntityRoutine
import com.example.shareroutine.data.source.room.entity.RoomEntityTodo

@Database(entities = [RoomEntityRoutine::class, RoomEntityTodo::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
}
