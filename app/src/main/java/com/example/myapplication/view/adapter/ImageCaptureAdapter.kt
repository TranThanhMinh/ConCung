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
import com.example.myapplication.model.product.Product
import com.example.myapplication.view.eventbus.CustomEvent
import com.squareup.picasso.Picasso
import org.greenrobot.eventbus.EventBus
import java.io.File


class ImageCaptureAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<String> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_capture, parent, false))
    }

    fun loadData(list: List<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list!![position]
        Picasso.with(context).load( File(item[position].toString())).into((holder as MyViewHolder).imView);
        holder.imView.setOnClickListener {
            EventBus.getDefault().post(CustomEvent(item[position].toString()))
        }
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imView = v.findViewById<ImageView>(R.id.imageView)
    }

}