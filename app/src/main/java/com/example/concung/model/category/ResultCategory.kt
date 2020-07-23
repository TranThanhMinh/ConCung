package com.example.concung.model.category

import com.example.concung.model.product.Product
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultCategory {

    @SerializedName("data")
    @Expose
    private var data: List<Category>? = null

    @SerializedName("product")
    @Expose
    private var product: List<Product>? = null

    @SerializedName("statusCode")
    @Expose
    private var statusCode: String? = null

    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    fun getCategory(): List<Category> {
        return data!!
    }

    fun setCategory(data: List<Category>) {
        this.data = data
    }


    fun getProduct(): List<Product> {
        return product!!
    }

    fun setProduct(product: List<Product>) {
        this.product = product
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