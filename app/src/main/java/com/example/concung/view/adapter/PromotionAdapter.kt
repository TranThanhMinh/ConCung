package com.example.concung.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.concung.R
import com.example.concung.model.product.Promotion


class PromotionAdapter(context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list:List<Promotion>
    private var context:Context = context
    init {
        list = ArrayList()
    }

    fun loadData(list:List<Promotion>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                var item = list[position]
                (holder as MyViewHolder).tvSTT.text = (position + 1).toString()
                holder.tvName.text = item.getName()

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_promotion, parent, false))
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

   class MyViewHolder(v:View) : RecyclerView.ViewHolder(v) {
       val tvSTT= v.findViewById<TextView>(R.id.tvSTT)
       val tvName = v.findViewById<TextView>(R.id.tvName)

    }


}