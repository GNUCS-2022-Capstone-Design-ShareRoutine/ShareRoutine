package com.example.shareroutine.di

import com.example.shareroutine.data.source.RoutineLocalDataSource
import com.example.shareroutine.data.source.RoutineRemoteDataSource
import com.example.shareroutine.data.source.realtime.RoutineDataSourceImplWithRealtime
import com.example.shareroutine.data.source.room.RoutineLocalDataSourceImplWithRoom
import com.example.shareroutine.data.source.room.dao.RoutineDao
import com.google.firebase.database.DatabaseReference
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
    fun provideRoutineDataSourceImplWithRoom(routineDao: RoutineDao):
            RoutineLocalDataSource = RoutineLocalDataSourceImplWithRoom(routineDao)

    @Singleton
    @Provides
    fun provideRoutineDataSourceImplWithRealtime(
        @RoutineDatabaseRef routineDbRef: DatabaseReference,
        @TodoDatabaseRef todoDbRef: DatabaseReference
    ):
            RoutineRemoteDataSource = RoutineDataSourceImplWithRealtime(routineDbRef, todoDbRef)
}