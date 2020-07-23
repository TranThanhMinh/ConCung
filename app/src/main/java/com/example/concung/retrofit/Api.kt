package com.example.concung.retrofit

import com.example.concung.model.cart.OrderAddress
import com.example.concung.model.cart.RequestStatus
import com.example.concung.model.cart.ResultGetOrder
import com.example.concung.model.cart.ResultNewOrder
import com.example.concung.model.category.ResultCategory
import com.example.concung.model.comment.ResquetComment
import com.example.concung.model.comment.ResultComment
import com.example.concung.model.comment.ResultStatus
import com.example.concung.model.home.RequestId
import com.example.concung.model.home.ResultApi
import com.example.concung.model.home.User
import com.example.concung.model.login.ResultLoginPhone
import com.example.concung.model.news.ResultNews
import com.example.concung.model.product.ResultIdProduct
import com.example.concung.model.product.ResultProduct
import com.example.concung.model.product.ResultUpload
import com.example.concung.model.trademark.ResultTrademark
import com.example.concung.model.user.Address
import com.example.concung.model.user.ResultAddress
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

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

    //get comment
    @POST("/MinhTran/public/api/typecategory")
    fun  getTypeCategory(@Body id: RequestId) :Call<ResultCategory>

    //update status of order
    @POST("/MinhTran/public/api/updatestatus")
    fun  updateStatus(@Body requestStatus : RequestStatus) :Call<ResultApi>
}