package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.dagger.Component.DaggerRetrofitComponent
import com.example.myapplication.dagger.Component.RetrofitComponent
import com.example.myapplication.model.User
import com.example.myapplication.model.login.ResultLoginPhone
import com.example.myapplication.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginRepository {
    private lateinit var data: MutableLiveData<Boolean>
    @Inject
    lateinit var api: Api
    init {
        val retrofitComponent: RetrofitComponent = DaggerRetrofitComponent.builder().build()
        retrofitComponent.inject(this)
    }
    fun hanldeLogin(user: User): LiveData<Boolean> {
        data = MutableLiveData()
        data.value = user.id_user.equals("minh") && user.pass_user.equals("1234")
        return data
    }

    fun loginUser(user: User):LiveData<ResultLoginPhone>{
        var list:MutableLiveData<ResultLoginPhone> = MutableLiveData()
        var call:Call<ResultLoginPhone> = api.getLoginPhone(user)
        call.enqueue(object : Callback<ResultLoginPhone>{
            override fun onFailure(call: Call<ResultLoginPhone>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultLoginPhone>, response: Response<ResultLoginPhone>) {
                list.value = response.body()
            }

        })
        return list
    }

}