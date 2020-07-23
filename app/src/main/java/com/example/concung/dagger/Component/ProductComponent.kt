package com.example.concung.dagger.Component

import com.example.concung.dagger.Module.ProductModule
import com.example.concung.view.product.InfoProductFragment
import dagger.Component

@Component(modules = [ProductModule::class])
interface ProductComponent {
    fun inject(activity: InfoProductFragment)
}