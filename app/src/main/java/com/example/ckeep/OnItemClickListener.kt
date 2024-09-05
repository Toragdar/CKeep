package com.example.ckeep

import com.example.ckeep.models.ItemModel

interface OnItemClickListener {
    fun onDeleteButtonClick(itemModel: ItemModel)
}