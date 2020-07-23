package com.example.concung.model.cart

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ListProduct {

    @SerializedName("id_order")
    @Expose
    private var idOrder: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("number")
    @Expose
    private var number: Int? = null

    @SerializedName("price")
    @Expose
    private var price: Int? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    fun getIdOrder(): String? {
        return idOrder
    }

    fun setIdOrder(idOrder: String?) {
        this.idOrder = idOrder
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getNumber(): Int {
        return number!!
    }

    fun setNumber(number: Int?) {
        this.number = number
    }

    fun getPrice(): Int {
        return price!!
    }

    fun setPrice(price: Int?) {
        this.price = price
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

}