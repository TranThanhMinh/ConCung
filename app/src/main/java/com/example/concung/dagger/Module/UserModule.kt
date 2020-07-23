package com.example.concung.dagger.Module

import com.example.concung.data.UserFB
import com.example.concung.model.home.Policy
import dagger.Module
import dagger.Provides


@Module
class UserModule {
    var user:UserFB?=null
    var policy: Policy?=null
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