package com.example.myapplication.util

import android.R
import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.model.User
import com.example.myapplication.view.HomeFragment


class Utility {
    companion object{
         var url: String? = null
         var id_user: String? = null

        fun saveUser(context: Context,user:User){
            val sharedPreferences  = context.getSharedPreferences("User", Context.MODE_PRIVATE)
            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("user",user.userName)
            editor.putString("pass",user.passWord)
            editor.apply()
        }

        fun saveUserId(context: Context,user:String,image:String){
            val sharedPreferences  = context.getSharedPreferences("UserId", Context.MODE_PRIVATE)
            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("id",user)
            editor.putString("image",image)
            editor.apply()
        }

        fun getInfo(context: Context): User{
            val sharedPreferences  = context.getSharedPreferences("User", Context.MODE_PRIVATE)
            return User(sharedPreferences.getString("user",null),sharedPreferences.getString("pass",null))
        }

        fun getUserId(context: Context): String{
            val sharedPreferences  = context.getSharedPreferences("UserId", Context.MODE_PRIVATE)
            val  id = sharedPreferences.getString("id","")
            return id!!
        }

        fun getUserImage(context: Context): String{
            val sharedPreferences  = context.getSharedPreferences("UserId", Context.MODE_PRIVATE)
            val  id = sharedPreferences.getString("image","")
            return id!!
        }

        fun deleteUser(context: Context){
            val sharedPreferences  = context.getSharedPreferences("User", Context.MODE_PRIVATE)
            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.commit()
        }
    }

}