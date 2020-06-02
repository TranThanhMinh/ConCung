package com.example.myapplication.retrofit

import com.example.myapplication.model.Category
import com.example.myapplication.model.Example
import com.example.myapplication.model.RequestId
import com.example.myapplication.model.ResultApi
import com.example.myapplication.model.product.ResultIdProduct
import com.example.myapplication.model.product.ResultProduct
import com.example.myapplication.model.trademark.ResultTrademark
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("/api/unknown")
    fun  getExample() :Call<Example>

    @GET("category")
    fun  getCategory() :Call<ResultApi>

    @GET("product")
    fun  getProduct() :Call<ResultProduct>

    @POST("idproduct")
    fun  getIdProduct(@Body id: RequestId) :Call<ResultIdProduct>

    @GET("trademark")
    fun  getTrademark() :Call<ResultTrademark>
}