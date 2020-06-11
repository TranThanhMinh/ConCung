package com.example.myapplication.view.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplication.R
import com.example.myapplication.model.comment.Image
import com.example.myapplication.model.product.Product
import com.squareup.picasso.Picasso
import java.io.File


class ImageCommentAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<Image> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_capture, parent, false))
    }

    fun loadData(list: List<Image>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list!![position]
        Picasso.with(context).load(item.getImage()).into((holder as MyViewHolder).imView);
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imView = v.findViewById<ImageView>(R.id.imageView)
    }

}