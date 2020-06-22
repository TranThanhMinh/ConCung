package com.example.myapplication.dagger.Component

import com.example.myapplication.dagger.Module.UserModule
import com.example.myapplication.view.Concung
import dagger.Component

@Component(modules = [UserModule::class])
interface UserComponent {
   fun inject(activity:Concung)
}