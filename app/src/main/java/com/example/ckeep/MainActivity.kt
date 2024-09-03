package com.example.ckeep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ckeep.data.Database
import com.example.ckeep.models.ItemModel
import com.example.ckeep.databinding.ActivityMainBinding
import com.example.ckeep.repositories.ItemRepository
import com.example.ckeep.viewModels.ItemFactory
import com.example.ckeep.viewModels.ItemViewModel

//TODO НЕ обновляется RecyclerView после изменения БД, надо допилить метод удаления айтемов из БД

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemDao = Database.getInstance(this).itemDAO
        val itemRepository = ItemRepository(itemDao)
        val itemFactory = ItemFactory(itemRepository)
        viewModel = ViewModelProvider(this, itemFactory).get(ItemViewModel::class.java)

        binding.itemCatalog.layoutManager = LinearLayoutManager(this)
        itemsAdapter = ItemsAdapter(this)
        binding.itemCatalog.adapter = itemsAdapter

        viewModel.items.observe(this) { items ->
            items?.let {
                itemsAdapter.submitList(it)
            }
        }

        binding.addNewItemButton.setOnClickListener {
            onAddNewItemButtonClick()
        }
    }

    override fun onDataShowButtonClick(itemModel: ItemModel, holder: ItemsAdapter.ItemHolder) {
        holder.ShowItemData()
    }

    override fun onDeleteButtonClick(itemModel: ItemModel) {
        viewModel.deleteItem(itemModel)
    }

    private fun onAddNewItemButtonClick(){
        val dialog = AddItemDialog()
        dialog.show(supportFragmentManager, "MyDialogFragment")
    }
}