package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventory.model.Things

@Database(entities = [Things::class], version = 1, exportSchema = false)
abstract class ThingDatabase: RoomDatabase() {

    abstract fun thingsDao(): ThingsDao

    companion object{
        @Volatile
        private var INSTANCE: ThingDatabase? = null

        fun getDatabase(context: Context): ThingDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ThingDatabase::class.java,
                    "thing_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}