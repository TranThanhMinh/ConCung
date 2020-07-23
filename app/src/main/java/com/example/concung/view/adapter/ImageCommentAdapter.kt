package com.example.concung.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.concung.R
import com.example.concung.model.comment.Image
import com.example.concung.view.eventbus.CustomEvent
import com.squareup.picasso.Picasso
import org.greenrobot.eventbus.EventBus


class ImageCommentAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<Image> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_capture, parent, false))
    }

    fun loadData(list: List<Image>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list!![position]
        Picasso.with(context).load(item.getImage()).into((holder as MyViewHolder).imView)
        holder.imView.setOnClickListener {
            EventBus.getDefault().post(CustomEvent(item.getImage()!!))
        }
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imView = v.findViewById<ImageView>(R.id.imageView)
    }

}