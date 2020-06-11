package com.example.myapplication.dagger.Module

import com.example.myapplication.retrofit.Api
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@Module
class RetrofitModule {
 // private val  URL :String  = "https://reqres.in"
    private val  URL :String  = "http://192.168.1.24/"
    private var api:Api? = null
    private var retrofit: Retrofit?= null

    @Provides
    fun providesAPi(): Api{
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()
        }
        api = retrofit!!.create(Api::class.java)
        return api!!
    }
}