package com.example.concung.view.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.concung.R
import com.example.concung.model.trademark.Trademark
import com.squareup.picasso.Picasso

class TrademarkAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<Trademark> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_trademark, parent, false))
    }

    fun loadData(list: List<Trademark>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       var item = list!![position]
        Picasso.with(context).load(item.getNameTrademark()).into(  (holder as MyViewHolder).imView)
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
         val imView = v.findViewById<ImageView>(R.id.imageView)
         val layout = v.findViewById<LinearLayout>(R.id.layout)
        init {
            val linearLayout = layout.layoutParams
            linearLayout.width =  Resources.getSystem().displayMetrics.widthPixels / 4 + Resources.getSystem().displayMetrics.widthPixels / 24
            linearLayout.height =  Resources.getSystem().displayMetrics.heightPixels / 10
            layout.layoutParams = linearLayout
        }
    }
}