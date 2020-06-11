package com.example.myapplication.model.comment

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResultStatus {

    @SerializedName("id_comment")
    @Expose
    private var idComment: Int? = null

    @SerializedName("statusCode")
    @Expose
    private var statusCode: String? = null

    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    fun getIdComment(): Int? {
        return idComment
    }

    fun setIdComment(idComment: Int?) {
        this.idComment = idComment
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