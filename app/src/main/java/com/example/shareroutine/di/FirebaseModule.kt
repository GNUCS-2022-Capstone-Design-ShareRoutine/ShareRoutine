package com.example.shareroutine.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun providePostDatabaseRef() : DatabaseReference {
        val db = FirebaseDatabase.getInstance(
            "https://shareroutine-default-rtdb.firebaseio.com/"
        )
        return db.getReference("community/posts")
    }
}