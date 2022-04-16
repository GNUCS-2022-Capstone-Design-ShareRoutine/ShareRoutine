package com.example.shareroutine.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shareroutine.data.model.Todo

@Dao
interface TodoDao {
    @Insert
    fun insert(vararg todo: Todo)

    @Update
    fun updateUsers(vararg todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("SELECT * FROM todo")
    fun getAll(): LiveData<List<Todo>>
}