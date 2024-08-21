package com.example.ckeep

interface OnItemClickListener {
    fun onDataShowButtonClick(itemModel: ItemModel, holder: ItemsAdapter.ItemHolder)
    fun onDeleteButtonClick(itemModel: ItemModel)
}