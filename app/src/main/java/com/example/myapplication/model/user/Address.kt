package com.example.myapplication.model.user

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Address {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

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

    @SerializedName("type")
    @Expose
    private var type: Int = 0

    private var id2: Int? = null

    private var type2: Int? = null



    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

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

    fun getType(): Int? {
        return type
    }

    fun setType(type: Int) {
        this.type = type
    }



    fun getId2(): Int? {
        return id2
    }

    fun setId2(id2: Int?) {
        this.id2 = id2
    }

    fun getType2(): Int? {
        return type2
    }

    fun setType2(type2: Int?) {
        this.type2 = type2
    }
}