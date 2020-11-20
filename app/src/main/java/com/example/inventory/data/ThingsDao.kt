package com.example.inventory.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.inventory.model.Things

@Dao
interface ThingsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addThing (things: Things)

    @Update
    suspend fun updateThing(things: Things)

    @Delete
    suspend fun deleteThing(things: Things)

    @Query("DELETE FROM things_table")
    suspend fun deleteAllThings()


    @Query("SELECT * FROM things_table ORDER BY id ASC")
    fun readAllData (): LiveData<List<Things>>



}