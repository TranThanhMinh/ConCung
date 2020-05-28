package com.example.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Example {
    @SerializedName("page")
    @Expose
    private var page: Int? = null
    @SerializedName("per_page")
    @Expose
    private var perPage: Int? = null
    @SerializedName("total")
    @Expose
    private var total: Int? = null
    @SerializedName("total_pages")
    @Expose
    private var totalPages: Int? = null
    @SerializedName("data")
    @Expose
    private var data: List<Datum>? = null
    @SerializedName("ad")
    @Expose
    private var ad: Ad? = null

    fun getPage(): Int? {
        return page
    }

    fun setPage(page: Int?) {
        this.page = page
    }

    fun getPerPage(): Int? {
        return perPage
    }

    fun setPerPage(perPage: Int?) {
        this.perPage = perPage
    }

    fun getTotal(): Int? {
        return total
    }

    fun setTotal(total: Int?) {
        this.total = total
    }

    fun getTotalPages(): Int? {
        return totalPages
    }

    fun setTotalPages(totalPages: Int?) {
        this.totalPages = totalPages
    }

    fun getData(): List<Datum>? {
        return data
    }

    fun setData(data: List<Datum>) {
        this.data = data
    }

    fun getAd(): Ad? {
        return ad
    }

    fun setAd(ad: Ad) {
        this.ad = ad
    }

}