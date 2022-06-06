package com.example.shareroutine.di

import com.example.shareroutine.data.source.PostDataSource
import com.example.shareroutine.data.source.PostWithRoutineDataSource
import com.example.shareroutine.data.source.realtime.PostDataSourceImplWithRealtime
import com.example.shareroutine.data.source.realtime.PostWithRoutineDataSourceImplWithRealtime
import com.google.firebase.database.DatabaseReference
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
    fun providePostDataSourceImplWithRealtime(@PostDatabaseRef dbRef: DatabaseReference): PostDataSource {
        return PostDataSourceImplWithRealtime(dbRef)
    }

    @Singleton
    @Provides
    fun providePostWithDataSourceImplWithRealtime(
        @TopDatabaseRef topDbRef: DatabaseReference,
        @PostDatabaseRef postDbRef: DatabaseReference,
        @RoutineDatabaseRef routineDbRef: DatabaseReference
    ): PostWithRoutineDataSource {
        return PostWithRoutineDataSourceImplWithRealtime(topDbRef, postDbRef, routineDbRef)
    }
}