package com.example.concung.model.cart

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResultNewOrder {

    @SerializedName("id_order")
    @Expose
    private var idOrder: Int? = null

    @SerializedName("statusCode")
    @Expose
    private var statusCode: String? = null

    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    fun getIdOrder(): Int? {
        return idOrder
    }

    fun setIdOrder(idOrder: Int?) {
        this.idOrder = idOrder
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