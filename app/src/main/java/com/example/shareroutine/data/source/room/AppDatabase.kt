package com.example.shareroutine.data.source.room

import androidx.room.*
import com.example.shareroutine.data.source.room.dao.RoutineDao
import com.example.shareroutine.data.source.room.dao.UsedTodoDao
import com.example.shareroutine.data.source.room.entity.RoomEntityRoutine
import com.example.shareroutine.data.source.room.entity.RoomEntityTodo
import com.example.shareroutine.data.source.room.entity.RoomEntityUsedTodo
import com.example.shareroutine.data.util.Converters

@Database(entities = [RoomEntityRoutine::class, RoomEntityTodo::class, RoomEntityUsedTodo::class], version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
    abstract fun usedTodoDao(): UsedTodoDao
}
