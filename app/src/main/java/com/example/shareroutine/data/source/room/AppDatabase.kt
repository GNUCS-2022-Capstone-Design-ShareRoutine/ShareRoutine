package com.example.shareroutine.data.source.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shareroutine.data.dao.RoutineDao
import com.example.shareroutine.data.dao.TodoDao
import com.example.shareroutine.data.model.Routine
import com.example.shareroutine.data.model.Todo

@Database(entities = [Routine::class, Todo::class], version = 1)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
    abstract  fun todoDao(): TodoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
