package com.example.shareroutine.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(vararg user: User)

    @Update
    fun updateUsers(vararg user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>
}