package com.example.concung.model.login

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class User {

     @SerializedName("id")
     @Expose
     private var id: Int? = null

     @SerializedName("id_user")
     @Expose
     private var idUser: String? = null

     @SerializedName("pass")
     @Expose
     private var pass: String? = null

     @SerializedName("name_user")
     @Expose
     private var nameUser: String? = null

     @SerializedName("gender")
     @Expose
     private var gender: String? = null

     @SerializedName("address")
     @Expose
     private var address: String? = null

     @SerializedName("image")
     @Expose
     private var image: String? = null


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

     fun getPass(): String? {
          return pass
     }

     fun setPass(pass: String?) {
          this.pass = pass
     }

     fun getNameUser(): String? {
          return nameUser
     }

     fun setNameUser(nameUser: String?) {
          this.nameUser = nameUser
     }

     fun getGender(): String? {
          return gender
     }

     fun setGender(gender: String?) {
          this.gender = gender
     }

     fun getAddress(): String? {
          return address
     }

     fun setAddress(address: String?) {
          this.address = address
     }

     fun getImage(): String? {
          return image
     }

     fun setImage(image: String?) {
          this.image = image
     }

}