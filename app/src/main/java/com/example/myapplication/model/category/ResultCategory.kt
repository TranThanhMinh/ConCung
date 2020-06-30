package com.example.myapplication.model.category

import com.example.myapplication.model.Category
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResultCategory {

    @SerializedName("data")
    @Expose
    private var data: List<Category>? = null

    @SerializedName("statusCode")
    @Expose
    private var statusCode: String? = null

    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    fun getData(): List<Category> {
        return data!!
    }

    fun setData(data: List<Category>) {
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