package com.example.myapplication.dagger.Module

import com.example.myapplication.data.UserFB
import dagger.Module
import dagger.Provides


@Module
class UserModule {
    var user:UserFB?=null
    @Provides
    fun userProvides() : UserFB{
         user = UserFB()
        return user!!
    }
}