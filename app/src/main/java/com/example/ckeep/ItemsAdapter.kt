package com.example.ckeep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.ckeep.models.ItemModel
import com.example.ckeep.databinding.MainItemBinding

class ItemsAdapter(private var items: ArrayList<ItemModel>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ItemsAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainItemBinding.inflate(layoutInflater, parent, false)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(items[position], itemClickListener)
    }

    class ItemHolder(private val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemModel: ItemModel, clickListener: OnItemClickListener) {
            binding.itemIcon.setImageResource(itemModel.itemIcon)
            binding.itemName.text = itemModel.itemName
            binding.itemLoginData.text = itemModel.itemLogin
            binding.itemPasswordData.text = itemModel.itemPassword

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

        //Обработка нажатия кнопки скрывающей/показывающей логин и пароль
        fun ShowItemData(){
            if(binding.itemLoginData.isInvisible){
                binding.itemLoginData.visibility = View.VISIBLE
                binding.itemPasswordData.visibility = View.VISIBLE

                binding.itemLoginDataIsHide.visibility = View.INVISIBLE
                binding.itemPasswordDataIsHide.visibility = View.INVISIBLE
            }
            else{
                binding.itemLoginData.visibility = View.INVISIBLE
                binding.itemPasswordData.visibility = View.INVISIBLE

                binding.itemLoginDataIsHide.visibility = View.VISIBLE
                binding.itemPasswordDataIsHide.visibility = View.VISIBLE
            }
        }
    }
}
