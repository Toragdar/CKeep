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

//TODO 1. Продумать как реализовывать шифрование и дешифрование данных. Как запрашивать ключ и тд

class MainActivity : AppCompatActivity(), OnItemClickListener,
    AddItemDialog.OnAddDialogResultListener, ConfirmDeleteDialog.ConfirmDeleteListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var itemDao: ItemDAO
    private lateinit var itemRepository: ItemRepository
    private lateinit var itemFactory: ItemFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        itemViewModel.items.observe(this, Observer { items ->
            Log.d("MainActivity", "Observed items: ${items.size}")
            itemsAdapter.submitList(items)
        })
    }

    override fun onAddDialogResult(itemName: String, itemLogin: String, itemPassword: String) {
        itemViewModel.startInsert(itemName, itemLogin, itemPassword)
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