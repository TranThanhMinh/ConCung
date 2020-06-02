package com.example.myapplication.model.trademark

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Trademark {
    @SerializedName("id_trademark")
    @Expose
    private var idTrademark: Int? = null

    @SerializedName("name_trademark")
    @Expose
    private var nameTrademark: String? = null

    fun getIdTrademark(): Int? {
        return idTrademark
    }

    fun setIdTrademark(idTrademark: Int?) {
        this.idTrademark = idTrademark
    }

    fun getNameTrademark(): String? {
        return nameTrademark
    }

    fun setNameTrademark(nameTrademark: String?) {
        this.nameTrademark = nameTrademark
    }
}