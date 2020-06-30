package com.example.myapplication.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productwatched")
class ProductWatched {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
     var id: String? = null

    @ColumnInfo(name = "name")
     var name: String? = null

    @ColumnInfo(name = "price")
     var price: String? = null

    @ColumnInfo(name = "image")
    var image: String? = null

    @ColumnInfo(name = "love")
    var love: String = "0"

}