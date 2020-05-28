package com.example.myapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Category
import com.squareup.picasso.Picasso

class CategoryAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false))
    }

    fun loadData(list: List<Category>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       var item = list!![position]
        Picasso.with(context).load(item.getImageCategory()).into(  (holder as MyViewHolder).imView)
        holder.tvName.text = item.getNameCategory()
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
         val imView = v.findViewById<ImageView>(R.id.imageView)
         val tvName = v.findViewById<TextView>(R.id.tvName)
    }
}