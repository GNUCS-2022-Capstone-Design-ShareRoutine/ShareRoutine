package com.example.shareroutine.di

import com.example.shareroutine.data.source.realtime.PostDataSourceImplWithRealtime
import com.example.shareroutine.data.source.realtime.dao.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostDataSourceModule {

    @Singleton
    @Provides
    fun providePostDataSourceImplWithRealtime(postDao: PostDao) = PostDataSourceImplWithRealtime(postDao)
}