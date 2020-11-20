package com.example.inventory.vewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.ThingDatabase
import com.example.inventory.repository.ThingRepository
import com.example.inventory.model.Things
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThingViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Things>>
    private val repository: ThingRepository


    init {
        val thingsDao = ThingDatabase.getDatabase(
            application
        ).thingsDao()
        repository = ThingRepository(thingsDao)
        readAllData = repository.readAllData
    }

    fun addThing(things: Things){
        viewModelScope.launch(Dispatchers.IO){
            repository.addThing(things)
        }

    }
    fun updateThing(things: Things) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateThing(things)
        }
    }

    fun deleteThing(things: Things){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteThing(things)
        }
    }

    fun deleteAllThings(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllThings()
        }
    }

}