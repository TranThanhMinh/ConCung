package com.example.concung.model.home

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResultApi {
    @SerializedName("data")
    @Expose
    private var data: Boolean? = null

    @SerializedName("statusCode")
    @Expose
    private var statusCode: String? = null

    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    fun getData(): Boolean? {
        return data
    }

    fun setData(data: Boolean?) {
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