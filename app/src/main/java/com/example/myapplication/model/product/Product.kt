package com.example.myapplication.model.product
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Product : Serializable{
    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("price")
    @Expose
    private var price: Int? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("id_promotion")
    @Expose
    private var idPromotion: Int? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("id_comment")
    @Expose
    private var idComment: Int? = null

    @SerializedName("id_category")
    @Expose
    private var idCategory: Int? = null

    @SerializedName("id_trademark")
    @Expose
    private var idTrademark: Int? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getPrice(): Int {
        return price!!
    }

    fun setPrice(price: Int) {
        this.price = price
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getIdPromotion(): Int? {
        return idPromotion
    }

    fun setIdPromotion(idPromotion: Int?) {
        this.idPromotion = idPromotion
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getIdComment(): Int? {
        return idComment
    }

    fun setIdComment(idComment: Int?) {
        this.idComment = idComment
    }

    fun getIdCategory(): Int? {
        return idCategory
    }

    fun setIdCategory(idCategory: Int?) {
        this.idCategory = idCategory
    }

    fun getIdTrademark(): Int? {
        return idTrademark
    }

    fun setIdTrademark(idTrademark: Int?) {
        this.idTrademark = idTrademark
    }
}