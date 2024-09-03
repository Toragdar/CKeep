package com.example.ckeep.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ckeep.models.ItemModel
import com.example.ckeep.repositories.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository): ViewModel() {

    val items = itemRepository.items

    fun startInsert(itemName: String, itemLogin: String, itemPassword: String){
        insertItem(ItemModel(0, itemName, itemLogin, itemPassword))
    }

    fun startDelete(){
        TODO()
    }

    fun insertItem(itemModel: ItemModel) = viewModelScope.launch {
        itemRepository.insertItem(itemModel)
    }

    fun deleteItem(itemModel: ItemModel) = viewModelScope.launch {
        itemRepository.insertItem(itemModel)
    }
}