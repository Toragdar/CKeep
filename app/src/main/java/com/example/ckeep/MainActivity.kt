package com.example.ckeep

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ckeep.data.Database
import com.example.ckeep.data.ItemDAO
import com.example.ckeep.models.ItemModel
import com.example.ckeep.databinding.ActivityMainBinding
import com.example.ckeep.repositories.ItemRepository
import com.example.ckeep.viewModels.ItemFactory
import com.example.ckeep.viewModels.ItemViewModel

//TODO добавить кнопку удаления введенного значения в поле ключа

class MainActivity : AppCompatActivity(), OnItemClickListener,
    AddItemDialog.OnAddDialogResultListener, ConfirmDeleteDialog.ConfirmDeleteListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var itemDao: ItemDAO
    private lateinit var itemRepository: ItemRepository
    private lateinit var itemFactory: ItemFactory
    private lateinit var encryptionKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        encryptionKey = intent.getStringExtra("ENCRYPTION_KEY").toString()

        initDao()
        initRecyclerView()
        refreshRecyclerView()

        binding.addNewItemButton.setOnClickListener {
            onAddNewItemButtonClick()
        }
    }
    private fun initDao(){
        itemDao = Database.getInstance(this).itemDAO
        itemRepository = ItemRepository(itemDao)
        itemFactory = ItemFactory(itemRepository)
        itemViewModel = ViewModelProvider(this, itemFactory).get(ItemViewModel::class.java)
    }

    private fun initRecyclerView(){
        binding.itemCatalog.layoutManager = LinearLayoutManager(this)
        itemsAdapter = ItemsAdapter(this)
        binding.itemCatalog.adapter = itemsAdapter
        binding.itemCatalog.itemAnimator = null
    }

    override fun onDeleteButtonClick(itemModel: ItemModel) {
        showConfirmDeleteDialog(itemModel)
    }

    private fun onAddNewItemButtonClick(){
        showAddItemDialog()
    }

    fun refreshRecyclerView() {
        itemViewModel.getDecryptedItems(encryptionKey).observe(this) { decryptedItems ->
            Log.d("MainActivity", "Observed items: ${decryptedItems.size}")
            itemsAdapter.submitList(decryptedItems)
        }
    }

    override fun onAddDialogResult(itemName: String, itemLogin: String, itemPassword: String) {
        itemViewModel.startInsert(itemName, itemLogin, itemPassword, encryptionKey)
    }

    override fun onDeleteConfirmed(itemModel: ItemModel) {
        itemViewModel.startDelete(itemModel)
    }

    private fun showAddItemDialog() {
        val dialog = AddItemDialog()
        dialog.show(supportFragmentManager, "MyDialogFragment")
    }

    private fun showConfirmDeleteDialog(itemModel: ItemModel) {
        val dialog = ConfirmDeleteDialog(itemModel)
        dialog.show(supportFragmentManager, "ConfirmDeleteDialog")
    }
}