package com.example.ckeep.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.ckeep.models.ItemModel
import com.example.ckeep.repositories.ItemRepository
import kotlinx.coroutines.launch


class ItemViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    val items: LiveData<List<ItemModel>> = itemRepository.items

    fun startInsert(itemName: String, itemLogin: String, itemPassword: String, encryptionKey: String) {
        viewModelScope.launch {
            insertItem(ItemModel(0, itemName, itemLogin, itemPassword), encryptionKey)
        }
    }

    private suspend fun insertItem(itemModel: ItemModel, encryptionKey: String): Boolean {
        return try {
            val encryptedItem = ItemModel(
                itemModel.id,
                itemModel.name,
                encryptString(itemModel.login, encryptionKey),
                encryptString(itemModel.password, encryptionKey)
            )

            itemRepository.insertItem(encryptedItem)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun startDelete(itemModel: ItemModel) {
        deleteItem(itemModel)
    }

    private fun deleteItem(itemModel: ItemModel) = viewModelScope.launch {
        itemRepository.deleteItem(itemModel)
    }

    fun encryptString(input: String, key: String): String {
        val keyDigits = key.map { it.toString().toInt() }
        val encrypted = StringBuilder()

        for (i in input.indices) {
            val shift = keyDigits[i % keyDigits.size]
            val shiftedChar = input[i] + shift
            encrypted.append(shiftedChar)
        }

        return encrypted.toString()
    }

    fun decryptString(input: String, key: String): String {
        val keyDigits = key.map { it.toString().toInt() }
        val decrypted = StringBuilder()

        for (i in input.indices) {
            val shift = keyDigits[i % keyDigits.size]
            val shiftedChar = input[i] - shift
            decrypted.append(shiftedChar)
        }

        return decrypted.toString()
    }

    fun getDecryptedItems(encryptionKey: String): LiveData<List<ItemModel>> {
        return items.map { encryptedItems ->
            encryptedItems.map { item ->
                ItemModel(
                    item.id,
                    item.name,
                    decryptString(item.login, encryptionKey),
                    decryptString(item.password, encryptionKey)
                )
            }
        }
    }
}