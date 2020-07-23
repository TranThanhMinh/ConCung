package com.example.concung.util

import android.content.Context
import android.content.SharedPreferences
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.concung.R
import com.example.concung.model.category.Category
import com.example.concung.model.home.User
import com.example.concung.model.news.News
import com.example.concung.model.product.Product
import com.example.concung.model.trademark.Trademark
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class Utility {
    companion object{
         var url: String? = null
         var id_user: String? = null
         var name_user: String? = null
         var image_user: String? = null
        var listCategory:List<Category> = ArrayList()
        var listTrademark:List<Trademark> = ArrayList()
        var listProduct:List<Product> = ArrayList()
        var listNews:List<News> = ArrayList()

        private var ft_add: FragmentTransaction? = null
        private var fm: FragmentManager? = null

        fun saveUser(context: Context,user: User){
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

        fun getInfo(context: Context): User {
            val sharedPreferences  = context.getSharedPreferences("User", Context.MODE_PRIVATE)
            return User(sharedPreferences.getString("user", null), sharedPreferences.getString("pass", null))
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

        fun replaceFragment(fm: FragmentManager,layout:Fragment){
            ft_add = fm!!.beginTransaction()
            ft_add!!.replace(R.id.fgLayout, layout).addToBackStack(null)
            ft_add!!.commit()
        }
        fun replaceHomeFragment(fm: FragmentManager,layout:Fragment){
            ft_add = fm!!.beginTransaction()
            ft_add!!.replace(R.id.flHome, layout).addToBackStack(null)
            ft_add!!.commit()
        }

        fun replaceProductFragment(fm: FragmentManager,layout:Fragment){
            ft_add = fm!!.beginTransaction()
            ft_add!!.replace(R.id.flProduct, layout).addToBackStack(null)
            ft_add!!.commit()
        }

        fun addProductFragment(fm: FragmentManager,layout:Fragment){
            ft_add = fm!!.beginTransaction()
            ft_add!!.add(R.id.flProduct, layout).addToBackStack(null)
            ft_add!!.commit()
        }

        fun addBottomDots(layout_dots: LinearLayout, size: Int, current: Int,context: Context) {
            val dots: Array<ImageView?> = arrayOfNulls<ImageView>(size)
            layout_dots.removeAllViews()
            for (i in dots.indices) {
                dots[i] = ImageView(context)
                val width_height = 20
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(width_height, width_height))
                params.setMargins(10, 10, 10, 10)
                dots[i]!!.layoutParams = params
                dots[i]!!.setImageResource(R.drawable.shape_circle_outline)
                layout_dots.addView(dots[i])
            }
            if (dots.isNotEmpty()) {
                dots[current]!!.setImageResource(R.drawable.shape_circle)
            }
        }
    }

}