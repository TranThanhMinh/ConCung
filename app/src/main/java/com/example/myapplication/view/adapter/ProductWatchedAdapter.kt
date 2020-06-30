package com.example.myapplication.view.adapter

import android.content.Context
import android.content.res.Resources
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.ProductWatched
import com.example.myapplication.model.Category
import com.example.myapplication.model.product.Product
import com.example.myapplication.util.Utility
import com.example.myapplication.view.InterfaceClick
import com.squareup.picasso.Picasso

class ProductWatchedAdapter(var context: Context,var click :InterfaceClick.EventProduct) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<ProductWatched> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false))
    }

    fun loadData(list: List<ProductWatched>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list!![position]
        Picasso.with(context).load(item.image).into(  (holder as MyViewHolder).imView)
        holder.tvName.text = item.name
        holder.tvPrice.text = Utility.currencyFormatter(item.price!!.toInt()) + context.resources.getString(R.string.txt_value)
        holder.layout.setOnClickListener {
            click.detailProductWatch(item)
        }

    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imView = v.findViewById<ImageView>(R.id.imageView)
        val tvName = v.findViewById<TextView>(R.id.tvName)
        val tvPrice = v.findViewById<TextView>(R.id.tvPrice)
        val layout = v.findViewById<LinearLayout>(R.id.layout)

        /*   init {
               val linearLayout = layout.layoutParams
               linearLayout.height =  Resources.getSystem().displayMetrics.heightPixels / 4
               layout.layoutParams = linearLayout
           }*/
    }

}