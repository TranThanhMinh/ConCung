package com.example.concung.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.concung.data.Cart
import com.example.concung.data.ProductWatched
import com.example.concung.model.home.RequestId
import com.example.concung.model.home.ResultApi
import com.example.concung.model.category.ResultCategory
import com.example.concung.model.home.User
import com.example.concung.model.cart.OrderAddress
import com.example.concung.model.cart.RequestStatus
import com.example.concung.model.cart.ResultGetOrder
import com.example.concung.model.cart.ResultNewOrder
import com.example.concung.model.comment.ResultStatus
import com.example.concung.model.comment.ResquetComment
import com.example.concung.model.comment.ResultComment
import com.example.concung.model.news.ResultNews
import com.example.concung.model.product.ResultIdProduct
import com.example.concung.model.product.ResultProduct
import com.example.concung.model.product.ResultUpload
import com.example.concung.model.trademark.ResultTrademark
import com.example.concung.model.user.Address
import com.example.concung.model.user.ResultAddress
import com.example.concung.repository.HomeRepository

class HomeViewModel : ViewModel() {

    private var homeRopository: HomeRepository?=null
    init {
        homeRopository = HomeRepository()
    }
    fun getData():LiveData<ResultCategory>{
        return homeRopository!!.getCategory()
    }

    fun getProduct():LiveData<ResultProduct>{
        return homeRopository!!.getProduct()
    }

    fun getIdProduct(id: RequestId):LiveData<ResultIdProduct>{
        return homeRopository!!.getIdProduct(id)
    }

    fun getTrademark():LiveData<ResultTrademark>{
        return homeRopository!!.getTrademark()
    }

    fun imageUpLoad(filePath:String,idComment:Int):LiveData<ResultUpload>{
        return homeRopository!!.imageUpLoad(filePath,idComment)
    }

    fun getNews():LiveData<ResultNews>{
        return homeRopository!!.getNews()
    }

    fun getAddress(id: User): LiveData<ResultAddress> {
        return homeRopository!!.getAddress(id)
    }


    fun getInsertComment(comment: ResquetComment): LiveData<ResultStatus> {
        return homeRopository!!.getInsertComment(comment)
    }

    fun getComment(id: RequestId): LiveData<ResultComment> {
        return homeRopository!!.getComment(id)
    }

    fun insertCart(cart: Cart){
        return homeRopository!!.insertCart(cart)
    }

    fun updateCart(cart: Cart){
        return homeRopository!!.updateCart(cart)
    }


    fun deleteCart(cart: Cart){
        return homeRopository!!.deleteCart(cart)
    }

    fun getCart():LiveData<List<Cart>>{
        return homeRopository!!.getCart()
    }

    fun getIdCart(id:String):LiveData<Cart>{
        return homeRopository!!.getIdCart(id)
    }

    fun updateOrInsert(address: Address):LiveData<ResultApi>{
        return homeRopository!!.updateOrInsert(address)
    }

    fun insertProduct(product: ProductWatched){
        return homeRopository!!.insertProduct(product)
    }


    fun updateProduct(product: ProductWatched){
        return homeRopository!!.updateProduct(product)
    }

    fun productExist(id: String):LiveData<List<ProductWatched>>{
        return homeRopository!!.productExist(id)
    }

    fun getProductWatched():LiveData<List<ProductWatched>>{
        return homeRopository!!.getProductWatched()
    }

    fun getProductLove():LiveData<List<ProductWatched>>{
        return homeRopository!!.getProductLove()
    }

    fun newOrder(address: OrderAddress):LiveData<ResultNewOrder>{
        return homeRopository!!.newOrder(address)
    }

    fun getOrder(user: User):LiveData<ResultGetOrder>{
        return homeRopository!!.getOrder(user)
    }

    fun getCategory(id: RequestId): LiveData<ResultCategory> {
        return homeRopository!!.getCategory(id)
    }

    fun updateStatus(requestStatus : RequestStatus): LiveData<ResultApi> {
        return homeRopository!!.updateStatus(requestStatus)
    }
}