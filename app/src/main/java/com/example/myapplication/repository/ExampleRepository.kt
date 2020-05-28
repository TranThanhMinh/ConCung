package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.dagger.Component.DaggerRetrofitComponent
import com.example.myapplication.dagger.Component.RetrofitComponent
import com.example.myapplication.model.Example
import com.example.myapplication.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ExampleRepository {
    private lateinit var example: MutableLiveData<Example>
    @Inject
    lateinit var api: Api

    init {
       val retrofitComponent: RetrofitComponent = DaggerRetrofitComponent.builder().build()
        retrofitComponent.inject(this)
    }

    fun getExample(): LiveData<Example> {
        example = MutableLiveData()
        var call: Call<Example> = api.getExample()
        call.enqueue(object : Callback<Example> {
            override fun onFailure(call: Call<Example>, t: Throwable) {
                example.value = null
            }

            override fun onResponse(call: Call<Example>, response: Response<Example>) {
                example.value = response.body()
            }
        })
        return example
    }
}