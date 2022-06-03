package com.example.shareroutine.di

import com.example.shareroutine.data.repository.PostRepositoryImpl
import com.example.shareroutine.data.repository.RoutineRepositoryImpl
import com.example.shareroutine.data.repository.UsedTodoRepositoryImpl
import com.example.shareroutine.data.repository.UserRepositoryImpl
import com.example.shareroutine.data.source.*
import com.example.shareroutine.domain.repository.PostRepository
import com.example.shareroutine.domain.repository.RoutineRepository
import com.example.shareroutine.domain.repository.UsedTodoRepository
import com.example.shareroutine.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRoutineRepository(
        local: RoutineLocalDataSource,
        remote: RoutineRemoteDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): RoutineRepository = RoutineRepositoryImpl(local, remote, ioDispatcher)

    @Singleton
    @Provides
    fun provideUsedTodoRepository(
        usedTodoDataSource: UsedTodoDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): UsedTodoRepository = UsedTodoRepositoryImpl(usedTodoDataSource, ioDispatcher)

    @Singleton
    @Provides
    fun providePostRepository(
        postDataSource: PostDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): PostRepository = PostRepositoryImpl(postDataSource, ioDispatcher)

    @Singleton
    @Provides
    fun provideUserRepository(
        remote: UserRemoteDataSource,
        auth: UserAuthDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): UserRepository = UserRepositoryImpl(remote, auth, ioDispatcher)
}