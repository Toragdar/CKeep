package com.example.ckeep.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ckeep.repositories.ItemRepository
import java.util.Locale.Category

class ItemFactory constructor(private val itemRepository: ItemRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return if(modelClass.isAssignableFrom(ItemViewModel::class.java)){
           ItemViewModel(this.itemRepository) as T
       }
       else{
            throw IllegalArgumentException("ViewModel Not Found")
       }
    }
}