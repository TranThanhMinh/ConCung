package com.example.concung.model.shop

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultShop {
    @SerializedName("data")
    @Expose
    private var data: List<AddressShop>? = null

    @SerializedName("statusCode")
    @Expose
    private var statusCode: String? = null

    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    fun getData(): List<AddressShop>? {
        return data
    }

    fun setData(data: List<AddressShop>?) {
        this.data = data
    }

    fun getStatusCode(): String? {
        return statusCode
    }

    fun setStatusCode(statusCode: String?) {
        this.statusCode = statusCode
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String?) {
        this.msg = msg
    }

}