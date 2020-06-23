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
import com.example.myapplication.util.Utility
import com.example.myapplication.view.InterfaceClick
import com.squareup.picasso.Picasso

class CartAdapter(var context: Context,var delete:InterfaceClick.EventCart) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<Cart> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false))
    }

    fun loadData(list: List<Cart>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       var item = list!![position]
        (holder as MyViewHolder ).tvMultiply.text = "x${item.number}"
        holder.tvName.text = item.name
        holder.tvPrice.text =  Utility.currencyFormatter( item.price!!.toInt())
        Picasso.with(context).load(item.image).into(holder.imProduct)
        val amount = item.price!!.toInt() * item.number!!
        holder.tvAmount.text = " = ${Utility.currencyFormatter(amount)}"+context.resources.getString(R.string.txt_value)

        holder.tvDelete.setOnClickListener {
            delete.deleteProduct(item)
        }

    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvName = v.findViewById<TextView>(R.id.tvName)
        val tvPrice = v.findViewById<TextView>(R.id.tvPrice)
        val tvMultiply =v.findViewById<TextView>(R.id.tvMultiply)
        val tvDelete =v.findViewById<TextView>(R.id.tvDelete)
        val tvAmount = v.findViewById<TextView>(R.id.tvAmount)
        val imProduct = v.findViewById<ImageView>(R.id.imProduct)
        init {
            val layout = imProduct!!.layoutParams
            layout.height = Resources.getSystem().displayMetrics.widthPixels / 4 - Resources.getSystem().displayMetrics.widthPixels / 20
            imProduct!!.layoutParams = layout
        }
    }

}