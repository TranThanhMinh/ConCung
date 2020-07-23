package com.example.concung.dagger.Component

import com.example.concung.dagger.Module.UserModule
import com.example.concung.view.ConcungActivity
import dagger.Component

@Component(modules = [UserModule::class])
interface UserComponent {
   fun inject(activity:ConcungActivity)
}