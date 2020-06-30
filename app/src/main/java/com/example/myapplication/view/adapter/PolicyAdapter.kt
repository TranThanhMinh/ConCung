package com.example.myapplication.view.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Cart
import com.example.myapplication.model.Policy
import com.example.myapplication.util.Utility
import com.example.myapplication.view.InterfaceClick
import com.squareup.picasso.Picasso

class PolicyAdapter(var context: Context,var click : ViewWeb) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<Policy> = ArrayList()

    interface  ViewWeb{
        fun show(url:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_policy, parent, false))
    }

    fun loadData(list: List<Policy>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list!![position]
        (holder as MyViewHolder).tvName.text = item.name
        holder.tvName.setOnClickListener {
            click.show(item.url!!)
        }
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvName = v.findViewById<TextView>(R.id.tvName)
    }

}