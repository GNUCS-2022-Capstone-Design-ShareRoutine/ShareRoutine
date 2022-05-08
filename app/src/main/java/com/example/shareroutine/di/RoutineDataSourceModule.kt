package com.example.shareroutine.di

import com.example.shareroutine.data.source.RoutineDataSource
import com.example.shareroutine.data.source.room.RoutineDataSourceImplWithRoom
import com.example.shareroutine.data.source.room.dao.RoutineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoutineDataSourceModule {

    @Singleton
    @Provides
    fun provideRoutineDataSourceImplWithRoom(routineDao: RoutineDao): RoutineDataSource = RoutineDataSourceImplWithRoom(routineDao)
}