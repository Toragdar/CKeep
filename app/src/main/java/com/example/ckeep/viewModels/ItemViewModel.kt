package com.example.ckeep.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ckeep.models.ItemModel
import com.example.ckeep.repositories.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository): ViewModel() {

    val items: LiveData<List<ItemModel>> = itemRepository.items

    fun startInsert(itemName: String, itemLogin: String, itemPassword: String){
        Log.d("ViewModel", "Start Insert new item")
        insertItem(ItemModel(0, itemName, itemLogin, itemPassword))
        Log.d("ViewModel", "Finish Insert new item")
    }

    fun startDelete(itemModel: ItemModel){
        Log.d("ViewModel", "Start Delete new item")
        deleteItem(itemModel)
    }

    fun insertItem(itemModel: ItemModel) = viewModelScope.launch {
        itemRepository.insertItem(itemModel)
    }

    fun deleteItem(itemModel: ItemModel) = viewModelScope.launch {
        itemRepository.deleteItem(itemModel)
    }
}