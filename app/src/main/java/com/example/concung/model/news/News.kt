package com.example.concung.model.news

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class News{

    @SerializedName("id_news")
    @Expose
    private var idNews: Int? = null

    @SerializedName("title_news")
    @Expose
    private var titleNews: String? = null

    @SerializedName("descriptipn")
    @Expose
    private var descriptipn: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    fun getIdNews(): Int? {
        return idNews
    }

    fun setIdNews(idNews: Int?) {
        this.idNews = idNews
    }

    fun getTitleNews(): String? {
        return titleNews
    }

    fun setTitleNews(titleNews: String?) {
        this.titleNews = titleNews
    }

    fun getDescriptipn(): String? {
        return descriptipn
    }

    fun setDescriptipn(descriptipn: String?) {
        this.descriptipn = descriptipn
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }
}