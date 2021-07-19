package com.example.a7minutesworkout.database

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert
    fun insertData(entityHistory:EntityHistory)

    @Delete
    fun deleteData(entityHistory: EntityHistory)

    @Update
    fun updateData(entity: EntityHistory)

    @Query("select * from history")
    fun getAllData():List<EntityHistory>
}