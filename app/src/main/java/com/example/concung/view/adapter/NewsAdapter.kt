package com.example.concung.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.concung.R
import com.example.concung.model.news.News
import com.squareup.picasso.Picasso

class NewsAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1)
        return MyTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news, parent, false))
        else {
            return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news_2, parent, false))
        }
    }

    fun loadData(list: List<News>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            1
        else
            2
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list!![position]
        when(holder.itemViewType){
           1->{
               Picasso.with(context).load(item.getImage()).into(  (holder as MyTitleViewHolder).imView)
               holder.tvTitle.text = item.getTitleNews()
               holder.tvDescription.text = item.getDescriptipn()
           }
            2->{
                (holder as MyViewHolder).tvTitle.text = item.getTitleNews()
            }
       }

    }

    class MyTitleViewHolder(v: View) : RecyclerView.ViewHolder(v) {
         val imView = v.findViewById<ImageView>(R.id.imageView)
         val tvTitle = v.findViewById<TextView>(R.id.tvTitle)
         val tvDescription = v.findViewById<TextView>(R.id.tvDescription)
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle = v.findViewById<TextView>(R.id.tvTitle)
    }
}