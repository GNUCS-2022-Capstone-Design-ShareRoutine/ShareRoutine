package com.example.shareroutine.di

import com.example.shareroutine.data.repository.RoutineRepositoryImpl
import com.example.shareroutine.data.source.RoutineDataSource
import com.example.shareroutine.domain.repository.RoutineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRoutineRepository(routineDataSource: RoutineDataSource): RoutineRepository = RoutineRepositoryImpl(routineDataSource)
}