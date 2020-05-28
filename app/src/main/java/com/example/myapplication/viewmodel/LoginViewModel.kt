package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.User
import com.example.myapplication.repository.LoginRepository

class LoginViewModel : ViewModel() {
    private lateinit var loginRepository: LoginRepository
    fun loginUser(user:User) : LiveData<Boolean> {
        loginRepository = LoginRepository()
        return loginRepository.hanldeLogin(user)
    }
}