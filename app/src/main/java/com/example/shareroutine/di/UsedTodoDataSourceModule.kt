package com.example.shareroutine.di

import com.example.shareroutine.data.source.UsedTodoDataSource
import com.example.shareroutine.data.source.room.UsedTodoDataSourceImplWithRoom
import com.example.shareroutine.data.source.room.dao.UsedTodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsedTodoDataSourceModule {

    @Singleton
    @Provides
    fun provideUsedTodoDataSourceImplWithRoom(usedTodoDao: UsedTodoDao):
            UsedTodoDataSource = UsedTodoDataSourceImplWithRoom(usedTodoDao)
}