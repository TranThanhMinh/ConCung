package com.example.myapplication.retrofit

import com.example.myapplication.model.*
import com.example.myapplication.model.cart.OrderAddress
import com.example.myapplication.model.cart.ResultGetOrder
import com.example.myapplication.model.cart.ResultNewOrder
import com.example.myapplication.model.category.ResultCategory
import com.example.myapplication.model.comment.ResquetComment
import com.example.myapplication.model.comment.ResultComment
import com.example.myapplication.model.comment.ResultStatus
import com.example.myapplication.model.login.ResultLoginPhone
import com.example.myapplication.model.news.ResultNews
import com.example.myapplication.model.product.ResultIdProduct
import com.example.myapplication.model.product.ResultProduct
import com.example.myapplication.model.product.ResultUpload
import com.example.myapplication.model.trademark.ResultTrademark
import com.example.myapplication.model.user.Address
import com.example.myapplication.model.user.ResultAddress
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    //
    @GET("/api/unknown")
    fun  getExample() :Call<Example>

    //get category
    @GET("/MinhTran/public/api/category")
    fun  getCategory() :Call<ResultCategory>

    //get all product
    @GET("/MinhTran/public/api/product")
    fun  getProduct() :Call<ResultProduct>

    //get product via id
    @POST("/MinhTran/public/api/idproduct")
    fun  getIdProduct(@Body id: RequestId) :Call<ResultIdProduct>

    //get trademark
    @GET("/MinhTran/public/api/trademark")
    fun  getTrademark() :Call<ResultTrademark>


    //check login
    @POST("/MinhTran/public/api/loginPhone")
    fun  getLoginPhone(@Body user: User) :Call<ResultLoginPhone>

    //get news
    @GET("/MinhTran/public/api/news")
    fun  getNews() :Call<ResultNews>

    @Multipart
    @POST("/MinhTran/public/api/upload")
    fun imageUpload(@Part file: MultipartBody.Part, @Part("name") requestBody: RequestBody,@Part("idComment") idComment: RequestBody,
                    @Part("nameImage") nameImage: RequestBody):Call<ResultUpload>

    //check login
    @POST("/MinhTran/public/api/insert")
    fun  getInsertComment(@Body user: ResquetComment) :Call<ResultStatus>

    //get comment
    @POST("/MinhTran/public/api/comment")
    fun  getComment(@Body id: RequestId) :Call<ResultComment>

    //get Address
    @POST("/MinhTran/public/api/address")
    fun  getAddress(@Body id: User) :Call<ResultAddress>

    //update or insert Address
    @POST("/MinhTran/public/api/updateorinsert")
    fun  updateOrInsert(@Body address: Address) :Call<ResultApi>

    //new order
    @POST("/MinhTran/public/api/order")
    fun  newOrder(@Body order: OrderAddress) :Call<ResultNewOrder>

    //get all order
    @POST("/MinhTran/public/api/getorder")
    fun  getOrder(@Body user: User) :Call<ResultGetOrder>
}