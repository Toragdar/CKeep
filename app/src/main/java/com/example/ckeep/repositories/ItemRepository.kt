package com.example.ckeep.repositories

import androidx.lifecycle.LiveData
import com.example.ckeep.data.ItemDAO
import com.example.ckeep.models.ItemModel

class ItemRepository(private val itemDAO: ItemDAO) {

    val items: LiveData<List<ItemModel>> = itemDAO.getAllItemsSortedByName()

    suspend fun insertItem(itemModel: ItemModel){
        itemDAO.insertItem(itemModel)
    }

    suspend fun deleteItem(itemModel: ItemModel){
        itemDAO.deleteItem(itemModel)
    }
}