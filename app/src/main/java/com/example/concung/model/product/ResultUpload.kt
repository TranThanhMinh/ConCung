package com.example.concung.model.product

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResultUpload {

    @SerializedName("url")
    @Expose
    private var url: String? = null

    @SerializedName("success")
    @Expose
    private var success: Int? = null

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String?) {
        this.url = url
    }

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

}