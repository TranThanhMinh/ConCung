package com.example.myapplication.dagger.Module

import com.example.myapplication.data.UserFB
import com.example.myapplication.model.Policy
import dagger.Module
import dagger.Provides


@Module
class UserModule {
    var user:UserFB?=null
    var policy:Policy?=null
    @Provides
    fun userProvides() : UserFB{
         user = UserFB()
        return user!!
    }

    @Provides
    fun policyProvides(): Policy {
        policy = Policy()
        return policy!!
    }
}