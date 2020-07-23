package com.example.concung.view.adapter


import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.concung.R
import com.example.concung.model.cart.ListProduct
import com.example.concung.util.Utility
import com.squareup.picasso.Picasso

class DetailOrderAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<ListProduct> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false))
    }

    fun loadData(list: List<ListProduct>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       var item = list[position]
        (holder as MyViewHolder ).tvMultiply.text = "x${item.getNumber()}"
        holder.tvName.text = item.getName()
        holder.tvPrice.text =  Utility.currencyFormatter( item.getPrice()!!.toInt())
        Picasso.with(context).load(item.getImage()).into(holder.imProduct)
        val amount = item.getPrice() * item.getNumber()
        holder.tvAmount.text = " = ${Utility.currencyFormatter(amount)} ${context.resources.getString(R.string.txt_value)}"
        holder.tvDelete.visibility = View.GONE

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
            imProduct.layoutParams = layout
        }
    }

}