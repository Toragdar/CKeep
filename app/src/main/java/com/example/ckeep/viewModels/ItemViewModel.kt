package com.example.ckeep.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ckeep.models.ItemModel
import com.example.ckeep.repositories.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    val items: LiveData<List<ItemModel>> = itemRepository.items

    fun startInsert(itemName: String, itemLogin: String, itemPassword: String) {
        viewModelScope.launch {
            insertItem(ItemModel(0, itemName, itemLogin, itemPassword))
        }
    }

    private suspend fun insertItem(itemModel: ItemModel): Boolean {
        return try {
            itemRepository.insertItem(itemModel)
            true
        } catch (e: Exception) {
            false
        }
    }
    fun startDelete(itemModel: ItemModel){
        deleteItem(itemModel)
    }

    fun deleteItem(itemModel: ItemModel) = viewModelScope.launch {
        itemRepository.deleteItem(itemModel)
    }
}