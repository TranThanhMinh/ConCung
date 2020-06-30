package com.example.myapplication.dagger.Component

import com.example.myapplication.dagger.Module.ProductModule
import com.example.myapplication.view.product.InfoProductFragment
import dagger.Component

@Component(modules = [ProductModule::class])
interface ProductComponent {
    fun inject(activity: InfoProductFragment)
}