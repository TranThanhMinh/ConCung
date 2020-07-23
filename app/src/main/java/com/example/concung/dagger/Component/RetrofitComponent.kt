package com.example.concung.dagger.Component

import com.example.concung.dagger.Module.RetrofitModule
import com.example.concung.repository.HomeRepository
import com.example.concung.repository.LoginRepository
import dagger.Component

@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {
    fun inject(fragment: HomeRepository)
    fun inject(fragment: LoginRepository)
}