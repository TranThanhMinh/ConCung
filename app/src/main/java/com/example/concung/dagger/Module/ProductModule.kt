package com.example.concung.dagger.Module

import com.example.concung.data.Cart
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