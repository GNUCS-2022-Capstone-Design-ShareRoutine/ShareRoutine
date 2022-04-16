package com.example.shareroutine.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shareroutine.data.model.User

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