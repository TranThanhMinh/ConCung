package com.example.myapplication.dagger.Component

import com.example.myapplication.dagger.Module.RetrofitModule
import com.example.myapplication.repository.ExampleRepository
import com.example.myapplication.repository.HomeRepository
import dagger.Component

@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {
    fun inject(fragment: ExampleRepository)
    fun inject(fragment: HomeRepository)
}