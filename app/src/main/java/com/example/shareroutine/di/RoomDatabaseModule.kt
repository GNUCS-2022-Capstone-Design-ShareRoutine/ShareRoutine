package com.example.shareroutine.di

import android.content.Context
import androidx.room.Room
import com.example.shareroutine.data.source.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "share_routine_db"
    ).build()

    @Singleton
    @Provides
    fun provideRoutineDao(db: AppDatabase) = db.routineDao()
}