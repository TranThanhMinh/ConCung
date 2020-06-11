package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.User
import com.example.myapplication.model.login.ResultLoginPhone
import com.example.myapplication.repository.LoginRepository

class LoginViewModel : ViewModel() {
    private lateinit var loginRepository: LoginRepository
    init {
        loginRepository = LoginRepository()
    }
    fun loginUser(user:User) : LiveData<Boolean> {
        return loginRepository.hanldeLogin(user)
    }

    fun login(user: User):LiveData<ResultLoginPhone>{
        return loginRepository.loginUser(user)
    }


}