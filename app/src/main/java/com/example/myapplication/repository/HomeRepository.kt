package com.example.myapplication.repository

import android.net.DnsResolver
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.dagger.Component.DaggerRetrofitComponent
import com.example.myapplication.dagger.Component.RetrofitComponent
import com.example.myapplication.model.Category
import com.example.myapplication.model.RequestId
import com.example.myapplication.model.ResultApi
import com.example.myapplication.model.product.ResultIdProduct
import com.example.myapplication.model.product.ResultProduct
import com.example.myapplication.model.trademark.ResultTrademark
import com.example.myapplication.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeRepository{

    @Inject
    lateinit var api: Api
    init {
        val retrofitComponent: RetrofitComponent = DaggerRetrofitComponent.builder().build()
        retrofitComponent.inject(this)
    }

    fun getCategory() : LiveData<ResultApi>{
      val list:MutableLiveData<ResultApi> = MutableLiveData()
      val  call:Call<ResultApi> = api.getCategory()
      call.enqueue(object : Callback<ResultApi>{
          override fun onFailure(call: Call<ResultApi>, t: Throwable) {
              list.value = null
          }

          override fun onResponse(call: Call<ResultApi>, response: Response<ResultApi>) {
              list.value = response.body()
          }

      })
      return list
  }

    fun getProduct():LiveData<ResultProduct>{
        val list:MutableLiveData<ResultProduct> = MutableLiveData()
        val call:Call<ResultProduct> = api.getProduct()
        call.enqueue(object :Callback<ResultProduct>{
            override fun onFailure(call: Call<ResultProduct>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultProduct>, response: Response<ResultProduct>) {
                list.value = response.body()
            }

        })
        return list
    }

    fun getTrademark():LiveData<ResultTrademark>{
        val list:MutableLiveData<ResultTrademark> = MutableLiveData()
        val call:Call<ResultTrademark> = api.getTrademark()
        call.enqueue(object :Callback<ResultTrademark>{
            override fun onFailure(call: Call<ResultTrademark>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultTrademark>, response: Response<ResultTrademark>) {
                list.value = response.body()
            }

        })
        return list
    }


    fun getIdProduct(id: RequestId):LiveData<ResultIdProduct>{
        val list:MutableLiveData<ResultIdProduct> = MutableLiveData()
        val call:Call<ResultIdProduct> = api.getIdProduct(id)
        call.enqueue(object :Callback<ResultIdProduct>{
            override fun onFailure(call: Call<ResultIdProduct>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultIdProduct>, response: Response<ResultIdProduct>) {
                list.value = response.body()
            }

        })
        return list
    }
}