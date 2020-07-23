package com.example.concung.model.category

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Category : Serializable {
    @SerializedName("id_page")
    @Expose
    private var idPage: Int? = null
    @SerializedName("id_category")
    @Expose
    private var idCategory: Int? = null

    @SerializedName("name_category")
    @Expose
    private var nameCategory: String? = null

    @SerializedName("image_category")
    @Expose
    private var imageCategory: String? = null

    fun getIdPage(): Int? {
        return idPage
    }

    fun setIdPage(idPage: Int?) {
        this.idPage = idPage
    }

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