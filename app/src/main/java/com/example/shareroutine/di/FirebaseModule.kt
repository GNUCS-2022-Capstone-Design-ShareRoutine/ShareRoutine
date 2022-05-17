package com.example.shareroutine.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    @PostDatabaseRef
    fun providePostDatabaseRef(): DatabaseReference {
        val db = FirebaseDatabase.getInstance(
            "https://shareroutine-default-rtdb.firebaseio.com/"
        )
        return db.getReference("community/posts")
    }

    @Singleton
    @Provides
    @RoutineDatabaseRef
    fun provideRoutineDatabaseRef(): DatabaseReference {
        val db = FirebaseDatabase.getInstance(
            "https://shareroutine-default-rtdb.firebaseio.com/"
        )
        return db.getReference("routines")
    }

    @Singleton
    @Provides
    @TodoDatabaseRef
    fun provideTodoDatabaseRef(): DatabaseReference {
        val db = FirebaseDatabase.getInstance(
            "https://shareroutine-default-rtdb.firebaseio.com/"
        )
        return db.getReference("todos")
    }

    @Singleton
    @Provides
    @UserDatabaseRef
    fun provideUserDatabaseRef(): DatabaseReference {
        val db = FirebaseDatabase.getInstance(
            "https://shareroutine-default-rtdb.firebaseio.com/"
        )
        return db.getReference("users")
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class PostDatabaseRef

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RoutineDatabaseRef

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class TodoDatabaseRef

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UserDatabaseRef