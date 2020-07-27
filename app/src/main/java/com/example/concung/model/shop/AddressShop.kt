package com.example.concung.model.shop

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class AddressShop {
    @SerializedName("id_shop")
    @Expose
    private var idShop: Int? = null

    @SerializedName("address")
    @Expose
    private var address: String? = null

    fun getIdShop(): Int? {
        return idShop
    }

    fun setIdShop(idShop: Int?) {
        this.idShop = idShop
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String?) {
        this.address = address
    }

}