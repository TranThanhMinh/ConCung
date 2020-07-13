package com.example.myapplication.model.cart

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Order : Serializable{

    @SerializedName("id_order")
    @Expose
    private var idOrder: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("address")
    @Expose
    private var address: String? = null

    @SerializedName("phone")
    @Expose
    private var phone: String? = null

    @SerializedName("status")
    @Expose
    private var status: Int? = null //0 cancel, 1 order, 2 delivered

    @SerializedName("datetime")
    @Expose
    private var datetime: Long? = null

    @SerializedName("listProduct")
    @Expose
    private var listProduct: List<ListProduct>? = null

    fun getIdOrder(): String {
        return idOrder!!
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

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String?) {
        this.address = address
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String?) {
        this.phone = phone
    }

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getDateTime(): Long? {
        return datetime
    }

    fun setDateTime(datetime: Long?) {
        this.datetime = datetime
    }

    fun getListProduct(): List<ListProduct> {
        return listProduct!!
    }

    fun setListProduct(listProduct: List<ListProduct>?) {
        this.listProduct = listProduct
    }
}