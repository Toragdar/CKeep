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

class MainActivity : AppCompatActivity(), OnItemClickListener {

    //TODO настроить сохранение данных в БД и подгрузку уже сохраненных данных

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var items: ArrayList<ItemModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        items = ArrayList()

        binding.itemCatalog.layoutManager = LinearLayoutManager(this)
        itemsAdapter = ItemsAdapter(items, this)
        binding.itemCatalog.adapter = itemsAdapter

        binding.addNewItemButton.setOnClickListener(){
            onAddNewItemButtonClick()
        }
    }

    override fun onDataShowButtonClick(itemModel: ItemModel, holder: ItemsAdapter.ItemHolder) {
        holder.ShowItemData()
    }

    override fun onDeleteButtonClick(itemModel: ItemModel) {
        //TODO
    }

    private fun onAddNewItemButtonClick(){
        val dialog = AddItemDialog()
        dialog.show(supportFragmentManager, "MyDialogFragment")
    }
}