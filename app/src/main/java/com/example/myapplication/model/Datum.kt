package com.example.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("year")
    @Expose
    private var year: Int? = null
    @SerializedName("color")
    @Expose
    private var color: String? = null
    @SerializedName("pantone_value")
    @Expose
    private var pantoneValue: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getYear(): Int? {
        return year
    }

    fun setYear(year: Int?) {
        this.year = year
    }

    fun getColor(): String? {
        return color
    }

    fun setColor(color: String) {
        this.color = color
    }

    fun getPantoneValue(): String? {
        return pantoneValue
    }

    fun setPantoneValue(pantoneValue: String) {
        this.pantoneValue = pantoneValue
    }
}