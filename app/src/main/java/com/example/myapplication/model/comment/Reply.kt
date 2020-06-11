package com.example.myapplication.model.comment

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Reply {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("id_comment")
    @Expose
    private var idComment: String? = null

    @SerializedName("id_user")
    @Expose
    private var idUser: String? = null

    @SerializedName("name_user")
    @Expose
    private var nameUser: String? = null

    @SerializedName("text_comment")
    @Expose
    private var textComment: String? = null

    @SerializedName("date_time")
    @Expose
    private var dateTime: String? = null

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

    fun getIdUser(): String? {
        return idUser
    }

    fun setIdUser(idUser: String?) {
        this.idUser = idUser
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

}