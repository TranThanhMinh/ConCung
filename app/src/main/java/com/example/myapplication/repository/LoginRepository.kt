package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.User

class LoginRepository {
    private lateinit var data: MutableLiveData<Boolean>
    fun hanldeLogin(user: User): LiveData<Boolean> {
        data = MutableLiveData()
        data.value = user.userName.equals("minh") && user.passWord.equals("1234")
        return data
    }
}