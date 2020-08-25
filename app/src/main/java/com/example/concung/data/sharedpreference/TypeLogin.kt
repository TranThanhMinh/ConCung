package com.example.concung.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences




class TypeLogin {
    companion object{
        fun saveLogin(context: Context,type:Int){
            val pref: SharedPreferences = context.getSharedPreferences("LoginType", 0) // 0 - for private mode
            val editor: SharedPreferences.Editor = pref.edit()
            editor.putInt("type",type)
            editor.commit()
        }

        fun getLogin(context: Context):Int{
            val pref: SharedPreferences = context.getSharedPreferences("LoginType", 0) // 0 - for private mode
            return  pref.getInt("type",0)
        }
    }
}