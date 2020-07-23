package com.example.concung.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResultLoginPhone {

    @SerializedName("user")
    @Expose
    private var user: List<User>? = null

    @SerializedName("statusCode")
    @Expose
    private var statusCode: String? = null

    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    fun getUser(): List<User>{
        return user!!
    }

    fun setUser(user: List<User>) {
        this.user = user
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