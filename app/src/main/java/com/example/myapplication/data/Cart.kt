package com.example.myapplication.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
class Cart() :Parcelable {
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

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        number = parcel.readValue(Int::class.java.classLoader) as? Int
        price = parcel.readString()
        image = parcel.readString()
    }



    companion object CREATOR : Parcelable.Creator<Cart> {
        override fun createFromParcel(parcel: Parcel): Cart {
            return Cart(parcel)
        }

        override fun newArray(size: Int): Array<Cart?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }
}