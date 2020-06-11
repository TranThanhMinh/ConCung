package com.example.myapplication.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplication.R
import com.squareup.picasso.Picasso


class ImageAdapter(var context: Context,var list:List<String>) : PagerAdapter() {
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_viewpager,container,false)
        var imageView = view.findViewById<ImageView>(R.id.imageView)
        Picasso.with(context).load(list[position]).error(R.drawable.ic_launcher_background).into(imageView)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View?
        container.removeView(view)
    }
}