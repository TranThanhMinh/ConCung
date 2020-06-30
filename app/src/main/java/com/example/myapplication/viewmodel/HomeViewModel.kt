package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Cart
import com.example.myapplication.data.ProductWatched
import com.example.myapplication.model.RequestId
import com.example.myapplication.model.ResultApi
import com.example.myapplication.model.category.ResultCategory
import com.example.myapplication.model.User
import com.example.myapplication.model.comment.ResultStatus
import com.example.myapplication.model.comment.ResquetComment
import com.example.myapplication.model.comment.ResultComment
import com.example.myapplication.model.news.ResultNews
import com.example.myapplication.model.product.ResultIdProduct
import com.example.myapplication.model.product.ResultProduct
import com.example.myapplication.model.product.ResultUpload
import com.example.myapplication.model.trademark.ResultTrademark
import com.example.myapplication.model.user.Address
import com.example.myapplication.model.user.ResultAddress
import com.example.myapplication.repository.HomeRepository

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
}