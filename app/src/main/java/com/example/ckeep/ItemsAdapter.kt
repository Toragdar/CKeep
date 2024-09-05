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
    ListAdapter<ItemModel, ItemsAdapter.ItemHolder>(ItemDiffCallback()) {

    private var expandedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainItemBinding.inflate(layoutInflater, parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener, position)
    }

    inner class ItemHolder(private val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemModel: ItemModel, clickListener: OnItemClickListener, position: Int) {
            binding.itemName.text = itemModel.name
            binding.itemLoginData.text = itemModel.login
            binding.itemPasswordData.text = itemModel.password

            val isExpanded = position == expandedPosition
            binding.itemLoginData.visibility = if (isExpanded) View.VISIBLE else View.INVISIBLE
            binding.itemPasswordData.visibility = if (isExpanded) View.VISIBLE else View.INVISIBLE

            binding.itemLoginDataIsHide.visibility = if (isExpanded) View.INVISIBLE else View.VISIBLE
            binding.itemPasswordDataIsHide.visibility = if (isExpanded) View.INVISIBLE else View.VISIBLE

            binding.itemDataShowButton.setOnClickListener {
                toggleExpansion(position)
            }

            binding.itemDeleteButton.setOnClickListener {
                clickListener.onDeleteButtonClick(itemModel)
            }
        }

        private fun toggleExpansion(position: Int) {
            val previousExpandedPosition = expandedPosition
            if (position == expandedPosition) {
                expandedPosition = RecyclerView.NO_POSITION
            } else {
                expandedPosition = position
            }
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(expandedPosition)
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }
    }
}


