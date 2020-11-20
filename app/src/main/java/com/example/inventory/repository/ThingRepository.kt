package com.example.inventory.repository

import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.LiveData
import com.example.inventory.data.ThingsDao
import com.example.inventory.model.Things

class ThingRepository(private val thingsDao: ThingsDao) {

    val readAllData: LiveData<List<Things>> = thingsDao.readAllData()

    suspend fun addThing(things: Things){
        thingsDao.addThing(things)
    }

    suspend fun updateThing(things: Things) {
        thingsDao.updateThing(things)
    }

    suspend fun deleteThing(things: Things){
        thingsDao.deleteThing(things)
    }

    suspend fun deleteAllThings(){
        thingsDao.deleteAllThings()
    }

}