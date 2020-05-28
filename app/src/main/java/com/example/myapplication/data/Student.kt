package com.example.myapplication.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
class Student {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idstudent")
    var mIdStudent:String?=null

    @ColumnInfo(name = "namestudent")
     var mNameStudent:String?=null

    @ColumnInfo(name = "class")
    var mClass:String?=null

    @ColumnInfo(name = "number")
    var mNumber:Int?=null


    @ColumnInfo(name = "gender")
    var mGender:String?=null

   var check:Boolean = false
   var viewItem:Boolean = false

}