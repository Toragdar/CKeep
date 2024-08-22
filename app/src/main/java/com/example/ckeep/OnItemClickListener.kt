package com.example.ckeep

import com.example.ckeep.models.ItemModel

interface OnItemClickListener {
    fun onDataShowButtonClick(itemModel: ItemModel, holder: ItemsAdapter.ItemHolder)
    fun onDeleteButtonClick(itemModel: ItemModel)
}