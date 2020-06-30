package com.example.myapplication.model.user


import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ResultAddress{
    @SerializedName("data")
    @Expose
    private var data: List<Address>? = null

    fun getData(): List<Address> {
        return data!!
    }

    fun setData(data: List<Address>) {
        this.data = data
    }
}