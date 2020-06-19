package com.example.myapplication.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_tb")
class UserFB {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: String? = null

    @ColumnInfo(name = "image")
    var image: String? = null

    @ColumnInfo(name = "name_user")
    var name_user: String? = null
}