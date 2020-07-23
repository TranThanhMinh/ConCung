package com.example.concung.model.comment

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Reply {

    @SerializedName("id_reply")
    @Expose
    private var idReply: String? = null

    @SerializedName("id_comment")
    @Expose
    private var idComment: String? = null

    @SerializedName("name_user")
    @Expose
    private var nameUser: String? = null

    @SerializedName("text_comment")
    @Expose
    private var textComment: String? = null

    @SerializedName("date_time")
    @Expose
    private var dateTime: String? = null

    @SerializedName("image")
    @Expose
    private var image: List<Image>? = null

    fun getIdReply(): String? {
        return idReply
    }

    fun setIdReply(idReply: String?) {
        this.idReply = idReply
    }

    fun getIdComment(): String? {
        return idComment
    }

    fun setIdComment(idComment: String?) {
        this.idComment = idComment
    }

    fun getNameUser(): String? {
        return nameUser
    }

    fun setNameUser(nameUser: String?) {
        this.nameUser = nameUser
    }

    fun getTextComment(): String? {
        return textComment
    }

    fun setTextComment(textComment: String?) {
        this.textComment = textComment
    }

    fun getDateTime(): String? {
        return dateTime
    }

    fun setDateTime(dateTime: String?) {
        this.dateTime = dateTime
    }

    fun getImage(): List<Image> {
        return image!!
    }

    fun setImage(image: List<Image>) {
        this.image = image
    }
}