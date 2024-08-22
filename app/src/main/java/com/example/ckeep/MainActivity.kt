package com.example.ckeep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ckeep.models.ItemModel
import com.example.ckeep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnItemClickListener {

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
    }

    override fun onDataShowButtonClick(itemModel: ItemModel, holder: ItemsAdapter.ItemHolder) {
        holder.ShowItemData()
    }

    override fun onDeleteButtonClick(itemModel: ItemModel) {
        //TODO
    }
}