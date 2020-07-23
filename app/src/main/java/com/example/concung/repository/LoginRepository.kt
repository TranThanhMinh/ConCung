package com.example.concung.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.concung.dagger.Component.DaggerRetrofitComponent
import com.example.concung.dagger.Component.RetrofitComponent
import com.example.concung.model.home.User
import com.example.concung.model.login.ResultLoginPhone
import com.example.concung.retrofit.Api
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