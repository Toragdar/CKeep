package com.example.ckeep.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.ckeep.models.ItemModel

@Dao
interface ItemDAO {

    @Insert
    suspend fun insertItem(itemModel: ItemModel)

    @Delete
    suspend fun deleteItem(itemModel: ItemModel)

    @Query("SELECT * FROM item_data_table")
    fun getAllItems(): LiveData<List<ItemModel>>

    @Query("SELECT * FROM item_data_table ORDER BY LOWER(item_name) ASC")
    fun getAllItemsSortedByName(): LiveData<List<ItemModel>>

}