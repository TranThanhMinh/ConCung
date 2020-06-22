package com.example.myapplication.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
class Cart {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id:String?=null

    @ColumnInfo(name = "name")
    var name:String?=null


    @ColumnInfo(name = "number")
    var number:Int?=null

    @ColumnInfo(name = "price")
    var price:String?=null

    @ColumnInfo(name = "image")
    var image:String?=null
}