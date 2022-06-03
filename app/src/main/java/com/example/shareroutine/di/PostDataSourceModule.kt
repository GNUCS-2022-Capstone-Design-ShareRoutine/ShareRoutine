package com.example.shareroutine.di

import com.example.shareroutine.data.source.PostDataSource
import com.example.shareroutine.data.source.realtime.PostDataSourceImplWithRealtime
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
}