package com.example.shareroutine.di

import com.example.shareroutine.data.source.realtime.dao.PostDao
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealtimeDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase() = Firebase.database

    @Singleton
    @Provides
    fun providePostDao(db: FirebaseDatabase) = PostDao(db.getReference("post"))
}