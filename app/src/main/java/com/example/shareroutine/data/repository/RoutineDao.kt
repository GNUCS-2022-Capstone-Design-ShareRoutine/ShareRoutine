package com.example.shareroutine.data.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shareroutine.data.model.Routine

@Dao
interface RoutineDao {
    @Insert
    fun insertAll(vararg routine: Routine)

    @Update
    fun updateUsers(vararg routine: Routine)

    @Delete
    fun delete(routineEntity: Routine)

    @Query("SELECT * FROM Routine")
    fun getAll(): LiveData<List<Routine>>

}