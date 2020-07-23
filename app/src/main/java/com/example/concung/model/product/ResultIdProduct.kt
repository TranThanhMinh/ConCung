package com.example.concung.model.product

import com.example.concung.model.comment.Comment
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResultIdProduct {
    @SerializedName("data")
    @Expose
    private var data: List<Product>? = null

    @SerializedName("comment")
    @Expose
    private var comment: List<Comment>? = null

    @SerializedName("promotion")
    @Expose
    private var promotion: List<Promotion>? = null

    @SerializedName("statusCode")
    @Expose
    private var statusCode: String? = null

    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    fun getData(): List<Product> {
        return data!!
    }

    fun setData(data: List<Product>) {
        this.data = data
    }

    fun getComment(): List<Comment> {
        return comment!!
    }

    fun setComment(comment: List<Comment>) {
        this.comment = comment
    }

    fun getPromotion(): List<Promotion> {
        return promotion!!
    }

    fun setPromotion(promotion: List<Promotion>) {
        this.promotion = promotion
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