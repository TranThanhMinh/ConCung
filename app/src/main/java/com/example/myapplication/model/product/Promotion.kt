package com.example.myapplication.model.product

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Promotion {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("id_promotion")
    @Expose
    private var idPromotion: Int? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getIdPromotion(): Int? {
        return idPromotion
    }

    fun setIdPromotion(idPromotion: Int?) {
        this.idPromotion = idPromotion
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
}