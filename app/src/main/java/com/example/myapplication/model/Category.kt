package com.example.myapplication.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Category {

    @SerializedName("id_category")
    @Expose
    private var idCategory: Int? = null

    @SerializedName("name_category")
    @Expose
    private var nameCategory: String? = null

    @SerializedName("image_category")
    @Expose
    private var imageCategory: String? = null

    fun getIdCategory(): Int? {
        return idCategory
    }

    fun setIdCategory(idCategory: Int?) {
        this.idCategory = idCategory
    }

    fun getNameCategory(): String? {
        return nameCategory
    }

    fun setNameCategory(nameCategory: String?) {
        this.nameCategory = nameCategory
    }

    fun getImageCategory(): String? {
        return imageCategory
    }

    fun setImageCategory(imageCategory: String?) {
        this.imageCategory = imageCategory
    }

}