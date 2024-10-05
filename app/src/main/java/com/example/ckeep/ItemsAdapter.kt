package com.example.ckeep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ckeep.models.ItemModel
import com.example.ckeep.databinding.MainItemBinding

class ItemsAdapter(private val itemClickListener: OnItemClickListener) :
    ListAdapter<ItemModel, ItemsAdapter.ItemHolder>(DIFF_CALLBACK) {

    private var visibleItemId: Int = RecyclerView.NO_POSITION

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemModel>() {
            override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainItemBinding.inflate(layoutInflater, parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener)
    }

    inner class ItemHolder(private val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemModel: ItemModel, clickListener: OnItemClickListener) {
            binding.accountName.text = itemModel.name

            val isVisible = itemModel.id == visibleItemId

            if(isVisible){
                binding.itemLoginData.text = itemModel.login
                binding.itemPasswordData.text = itemModel.password
            }
            else{
                binding.itemLoginData.text = "*******"
                binding.itemPasswordData.text = "*******"
            }

            binding.itemDataShowButton.setOnClickListener {
                toggleItemVisibility(itemModel.id)
            }

            binding.itemDeleteButton.setOnClickListener {
                clickListener.onDeleteButtonClick(itemModel)
            }
        }

        private fun toggleItemVisibility(itemId: Int) {
            val previousExpandedItemId = visibleItemId
            if (itemId == visibleItemId) {
                visibleItemId = RecyclerView.NO_POSITION
            } else {
                visibleItemId = itemId
            }
            val previousPosition = currentList.indexOfFirst { it.id == previousExpandedItemId }
            val newPosition = currentList.indexOfFirst { it.id == visibleItemId }

            if (previousPosition >= 0) notifyItemChanged(previousPosition)
            if (newPosition >= 0) notifyItemChanged(newPosition)
        }
    }
}



