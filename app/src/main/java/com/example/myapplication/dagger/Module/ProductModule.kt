package com.example.myapplication.dagger.Module

import com.example.myapplication.data.Cart
import dagger.Module
import dagger.Provides

@Module
class ProductModule {
     var cart:Cart? = null

    @Provides
    fun cartProvides() : Cart{
        cart = Cart()
        return cart!!
    }
}