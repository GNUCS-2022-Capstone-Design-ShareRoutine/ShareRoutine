package com.example.shareroutine.data.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shareroutine.data.model.Routine

@Dao
interface RoutineDao {
    @Insert
    fun insert(vararg routine: Routine)

    @Update
    fun updateUsers(vararg routine: Routine)

    @Delete
    fun delete(routine: Routine)

    @Query("SELECT * FROM Routine")
    fun getAll(): LiveData<List<Routine>>

}