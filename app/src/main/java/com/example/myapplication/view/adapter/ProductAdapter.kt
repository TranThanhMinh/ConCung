package com.example.myapplication.view.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Category
import com.example.myapplication.model.product.Product
import com.example.myapplication.view.InterfaceClick
import com.squareup.picasso.Picasso

class ProductAdapter(var context: Context,var click :InterfaceClick.Product) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<Product> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_trademark, parent, false))
    }

    fun loadData(list: List<Product>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       var item = list!![position]
        Picasso.with(context).load(item.getImage()).into(  (holder as MyViewHolder).imView)
        holder.tvName.text = item.getName()
        holder.layout.setOnClickListener {
            click.detailProduct(item.getId().toString())
        }

    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
         val imView = v.findViewById<ImageView>(R.id.imageView)
         val tvName = v.findViewById<TextView>(R.id.tvName)
         val layout = v.findViewById<LinearLayout>(R.id.layout)
    }

}