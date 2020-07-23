package com.example.concung.app

import android.app.Application
import com.example.concung.data.RoomData

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RoomData.getData(this)
    }
}