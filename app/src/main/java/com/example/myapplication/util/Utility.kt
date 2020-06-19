package com.example.myapplication.util

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.model.Category
import com.example.myapplication.model.ResultApi
import com.example.myapplication.model.User
import com.example.myapplication.model.news.News
import com.example.myapplication.model.product.Product
import com.example.myapplication.model.trademark.Trademark
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Utility {
    companion object{
         var url: String? = null
         var id_user: String? = null
         var name_user: String? = null
        var listCategory:List<Category> = ArrayList()
        var listTrademark:List<Trademark> = ArrayList()
        var listProduct:List<Product> = ArrayList()
        var listNews:List<News> = ArrayList()

        fun saveUser(context: Context,user:User){
            val sharedPreferences  = context.getSharedPreferences("User", Context.MODE_PRIVATE)
            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("user",user.id_user)
            editor.putString("pass",user.pass_user)
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

        fun currencyFormatter(num: Int): String? {
            val m = num.toDouble()
            val formatter = DecimalFormat("###,###,###")
            return formatter.format(m)
        }

        fun convertStringToDateTime(time:Long):String{
            val sdf = SimpleDateFormat("hh:mm dd-MM-yyyy")
            return  sdf.format(time)
        }
    }

}