package com.example.myapplication.app

import android.app.Application
import com.example.myapplication.data.RoomData

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RoomData.getData(this)
    }
}