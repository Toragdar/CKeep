package com.example.ckeep.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ckeep.models.ItemModel

@Database(entities = [ItemModel::class], version = 1)
abstract class Database: RoomDatabase(){
    abstract val itemDAO: ItemDAO

    companion object{
        @Volatile
        private var INSTANCE: com.example.ckeep.data.Database? = null
        fun getInstance(context: Context): com.example.ckeep.data.Database{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.ckeep.data.Database::class.java,
                        "database"
                    ).build()
                }
                return instance
            }
        }
    }
}

