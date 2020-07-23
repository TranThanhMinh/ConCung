package com.example.concung.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.concung.model.home.User
import com.example.concung.model.login.ResultLoginPhone
import com.example.concung.repository.LoginRepository

class LoginViewModel : ViewModel() {
    private var loginRepository: LoginRepository = LoginRepository()
    fun loginUser(user: User) : LiveData<Boolean> {
        return loginRepository.hanldeLogin(user)
    }

    fun login(user: User):LiveData<ResultLoginPhone>{
        return loginRepository.loginUser(user)
    }


}