package com.example.myapplication.model.comment

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Comment {

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

    @SerializedName("reply")
    @Expose
    private var reply: List<Reply>? = null

    fun getIdComment(): String? {
        return idComment
    }

    fun setIdComment(idComment: String?) {
        this.idComment = idComment
    }

    fun getTextComment(): String? {
        return textComment
    }

    fun setTextComment(textComment: String?) {
        this.textComment = textComment
    }

    fun getNameUser(): String? {
        return nameUser
    }

    fun setNameUser(nameUser: String?) {
        this.nameUser = nameUser
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


    fun getReply(): List<Reply> {
        return reply!!
    }

    fun setReply(reply: List<Reply>) {
        this.reply = reply
    }
}