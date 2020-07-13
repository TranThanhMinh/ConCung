package com.example.myapplication.model.cart

import com.example.myapplication.data.Cart
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class OrderAddress {

    @SerializedName("id_user")
    @Expose
    private var idUser: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("phone")
    @Expose
    private var phone: String? = null

    @SerializedName("address")
    @Expose
    private var address: String? = null

    @SerializedName("datetime")
    @Expose
    private var datetime: Long? = null

    @SerializedName("list")
    @Expose
    private var list: List<Cart>? = null

    fun getIdUser(): String? {
        return idUser
    }

    fun setIdUser(idUser: String?) {
        this.idUser = idUser
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String?) {
        this.phone = phone
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String?) {
        this.address = address
    }

    fun getDateTime(): Long? {
        return datetime
    }

    fun setDateTime(datetime: Long?) {
        this.datetime = datetime
    }

    fun getList(): List<Cart> {
        return list!!
    }

    fun setList(list: List<Cart>?) {
        this.list = list
    }
}