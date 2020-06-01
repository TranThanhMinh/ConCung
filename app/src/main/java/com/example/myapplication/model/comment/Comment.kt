package com.example.myapplication.model.comment

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Comment {

    @SerializedName("id_comment")
    @Expose
    private var idComment: Int? = null

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("text_comment")
    @Expose
    private var textComment: String? = null

    fun getIdComment(): Int? {
        return idComment
    }

    fun setIdComment(idComment: Int?) {
        this.idComment = idComment
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getTextComment(): String? {
        return textComment
    }

    fun setTextComment(textComment: String?) {
        this.textComment = textComment
    }

}