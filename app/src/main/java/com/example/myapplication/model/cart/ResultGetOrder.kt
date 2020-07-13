package com.example.myapplication.model.cart

import com.example.myapplication.model.Datum

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResultGetOrder {
    @SerializedName("data")
    @Expose
    private var data: List<Order>? = null

    @SerializedName("statusCode")
    @Expose
    private var statusCode: String? = null

    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    fun getData(): List<Order> {
        return data!!
    }

    fun setData(data: List<Order>) {
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