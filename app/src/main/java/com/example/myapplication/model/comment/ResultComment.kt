package com.example.myapplication.model.comment

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResultComment {

    @SerializedName("comment")
    @Expose
    private var comment: ArrayList<Comment>? = null

    @SerializedName("statusCode")
    @Expose
    private var statusCode: String? = null

    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    fun getComment(): ArrayList<Comment> {
        return comment!!
    }

    fun setComment(comment: ArrayList<Comment>) {
        this.comment = comment
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