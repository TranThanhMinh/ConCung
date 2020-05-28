package com.example.myapplication.retrofit

import com.example.myapplication.model.Category
import com.example.myapplication.model.Example
import com.example.myapplication.model.ResultApi
import com.example.myapplication.model.product.ResultProduct
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("/api/unknown")
    fun  getExample() :Call<Example>

    @GET("/MinhTran/public/api/category")
    fun  getCategory() :Call<ResultApi>

    @GET("/MinhTran/public/api/product")
    fun  getProduct() :Call<ResultProduct>
}