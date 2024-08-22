package com.example.ckeep.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_data_table")
data class ItemModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    var id: Int,

    @ColumnInfo(name = "item_name")
    var name: String,

    @ColumnInfo(name = "item_login")
    var login: String,

    @ColumnInfo(name = "item_password")
    var password: String
)