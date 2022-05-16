package com.example.shareroutine.di

import com.example.shareroutine.data.repository.PostRepositoryImpl
import com.example.shareroutine.data.repository.RoutineRepositoryImpl
import com.example.shareroutine.data.source.PostDataSource
import com.example.shareroutine.data.source.RoutineLocalDataSource
import com.example.shareroutine.data.source.RoutineRemoteDataSource
import com.example.shareroutine.domain.repository.PostRepository
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
    fun provideRoutineRepository(
        local: RoutineLocalDataSource,
        remote: RoutineRemoteDataSource
    ): RoutineRepository = RoutineRepositoryImpl(local, remote)

    @Singleton
    @Provides
    fun providePostRepository(postDataSource: PostDataSource): PostRepository = PostRepositoryImpl(postDataSource)
}