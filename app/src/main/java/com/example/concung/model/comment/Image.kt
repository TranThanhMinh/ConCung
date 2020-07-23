package com.example.concung.model.comment

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Image {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("id_comment")
    @Expose
    private var idComment: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getIdComment(): String? {
        return idComment
    }

    fun setIdComment(idComment: String?) {
        this.idComment = idComment
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

}