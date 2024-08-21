package com.example.ckeep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

        setMockData(items)

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

//тестовые данные
private fun setMockData(items: ArrayList<ItemModel>){
    items.add(ItemModel(R.drawable.default_item_icon, "Steam", "User1", "12345"))
    items.add(ItemModel(R.drawable.default_item_icon, "EGS", "User2", "QWERTY12345!"))
    items.add(ItemModel(R.drawable.default_item_icon, "Uplay", "User5", "ShShSh097"))
    items.add(ItemModel(R.drawable.default_item_icon, "Git", "User7", "PasQWE!RTY"))
    items.add(ItemModel(R.drawable.default_item_icon, "Etsy", "User21", "Cash$"))
    items.add(ItemModel(R.drawable.default_item_icon, "Steam", "User1", "12345"))
    items.add(ItemModel(R.drawable.default_item_icon, "EGS", "User2", "QWERTY12345!"))
    items.add(ItemModel(R.drawable.default_item_icon, "Uplay", "User5", "ShShSh097"))
}