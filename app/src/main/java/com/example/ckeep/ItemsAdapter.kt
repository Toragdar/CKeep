package com.example.ckeep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ckeep.models.ItemModel
import com.example.ckeep.databinding.MainItemBinding

class ItemsAdapter(private val itemClickListener: OnItemClickListener) :
    ListAdapter<ItemModel, ItemsAdapter.ItemHolder>(DIFF_CALLBACK) {

    private var expandedItemId: Int = RecyclerView.NO_POSITION

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
            binding.itemName.text = itemModel.name
            binding.itemLoginData.text = itemModel.login
            binding.itemPasswordData.text = itemModel.password

            val isExpanded = itemModel.id == expandedItemId
            binding.itemLoginData.visibility = if (isExpanded) View.VISIBLE else View.INVISIBLE
            binding.itemPasswordData.visibility = if (isExpanded) View.VISIBLE else View.INVISIBLE

            binding.itemLoginDataIsHide.visibility = if (isExpanded) View.INVISIBLE else View.VISIBLE
            binding.itemPasswordDataIsHide.visibility = if (isExpanded) View.INVISIBLE else View.VISIBLE

            binding.itemDataShowButton.setOnClickListener {
                toggleExpansion(itemModel.id)
            }

            binding.itemDeleteButton.setOnClickListener {
                clickListener.onDeleteButtonClick(itemModel)
            }
        }

        private fun toggleExpansion(itemId: Int) {
            val previousExpandedItemId = expandedItemId
            if (itemId == expandedItemId) {
                expandedItemId = RecyclerView.NO_POSITION
            } else {
                expandedItemId = itemId
            }
            val previousPosition = currentList.indexOfFirst { it.id == previousExpandedItemId }
            val newPosition = currentList.indexOfFirst { it.id == expandedItemId }

            if (previousPosition >= 0) notifyItemChanged(previousPosition)
            if (newPosition >= 0) notifyItemChanged(newPosition)
        }
    }
}



