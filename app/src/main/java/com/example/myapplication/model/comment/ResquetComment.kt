package com.example.myapplication.model.comment

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResquetComment {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("id_comment")
    @Expose
    private var id_comment: String? = null

    @SerializedName("id_user")
    @Expose
    private var id_user: String? = null

    @SerializedName("name_user")
    @Expose
    private var name_user: String? = null

    @SerializedName("text_comment")
    @Expose
    private var text_comment: String? = null

    @SerializedName("date_time")
    @Expose
    private var date_time: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getIdUser(): String? {
        return id_user
    }

    fun setIdComment(id_comment: String?) {
        this.id_comment = id_comment
    }

    fun getIdComment(): String {
        return id_comment!!
    }

    fun setIdUser(idUser: String?) {
        this.id_user = idUser
    }

    fun getNameUser(): String? {
        return name_user
    }

    fun setNameUser(nameUser: String?) {
        this.name_user = nameUser
    }

    fun getTextComment(): String? {
        return text_comment
    }

    fun setTextComment(textComment: String?) {
        this.text_comment = textComment
    }

    fun getDateTime(): String? {
        return date_time
    }

    fun setDateTime(dateTime: String?) {
        this.date_time = dateTime
    }

}