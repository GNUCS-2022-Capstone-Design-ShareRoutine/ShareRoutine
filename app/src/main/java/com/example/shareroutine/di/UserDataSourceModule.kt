package com.example.shareroutine.di

import com.example.shareroutine.data.source.UserAuthDataSource
import com.example.shareroutine.data.source.UserRemoteDataSource
import com.example.shareroutine.data.source.auth.UserDataSourceImplWithAuth
import com.example.shareroutine.data.source.realtime.UserDataSourceImplWithRealtime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataSourceModule {

    @Singleton
    @Provides
    fun provideUserDataSourceImplWithRealtime(
        @UserDatabaseRef dbRef: DatabaseReference
    ):
            UserRemoteDataSource = UserDataSourceImplWithRealtime(dbRef)

    @Singleton
    @Provides
    fun provideUserDataSourceImplWithAuth(auth: FirebaseAuth):
            UserAuthDataSource = UserDataSourceImplWithAuth(auth)
}