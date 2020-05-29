package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.UserFB
import com.example.myapplication.repository.ConCungRepository

class ConCungViewModel:ViewModel() {
    private var concung: ConCungRepository?=null

    init {
        concung = ConCungRepository()
    }

    public fun getUser():LiveData<List<UserFB>>{
        return concung!!.getAllUser()
    }

    fun insert(userFB: UserFB){
        concung!!.insertUserFB(userFB)
    }


    fun deleteUser(userFB: UserFB){
        concung!!.deleteUserFB(userFB)
    }
}