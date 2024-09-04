package com.example.ckeep

import android.util.Log
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainItemBinding.inflate(layoutInflater, parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener)
    }

    class ItemHolder(private val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemModel: ItemModel, clickListener: OnItemClickListener) {
            binding.itemName.text = itemModel.name
            binding.itemLoginData.text = itemModel.login
            binding.itemPasswordData.text = itemModel.password

            binding.itemLoginData.visibility = View.INVISIBLE
            binding.itemPasswordData.visibility = View.INVISIBLE

            binding.itemLoginDataIsHide.visibility = View.VISIBLE
            binding.itemPasswordDataIsHide.visibility = View.VISIBLE

            binding.itemDataShowButton.setOnClickListener {
                clickListener.onDataShowButtonClick(itemModel, this)
            }

            binding.itemDeleteButton.setOnClickListener {
                clickListener.onDeleteButtonClick(itemModel)
            }
        }

        fun ShowItemData() {
            if (binding.itemLoginData.isInvisible) {
                binding.itemLoginData.visibility = View.VISIBLE
                binding.itemPasswordData.visibility = View.VISIBLE

                binding.itemLoginDataIsHide.visibility = View.INVISIBLE
                binding.itemPasswordDataIsHide.visibility = View.INVISIBLE
            } else {
                binding.itemLoginData.visibility = View.INVISIBLE
                binding.itemPasswordData.visibility = View.INVISIBLE

                binding.itemLoginDataIsHide.visibility = View.VISIBLE
                binding.itemPasswordDataIsHide.visibility = View.VISIBLE
            }
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

