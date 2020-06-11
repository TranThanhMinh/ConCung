package com.example.myapplication.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Student
import com.example.myapplication.model.comment.Comment
import com.example.myapplication.model.comment.Reply
import com.example.myapplication.model.product.Product
import com.example.myapplication.model.product.Promotion
import com.example.myapplication.util.Utility.Companion.convertStringToDateTime
import com.example.myapplication.view.InterfaceClick


class ReplyCommentAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: List<Reply>

    init {
        list = ArrayList()
    }


    fun loadData(list: List<Reply>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list[position]
        (holder as MyViewHolder).tvComment.text = item.getTextComment()
        holder.tvName.text = item.getNameUser()
        holder.tvDateTime.text = convertStringToDateTime(item.getDateTime()!!.toLong())
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_reply_comment, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("WrongConstant")
    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvComment = v.findViewById<TextView>(R.id.tvComment)
        val tvName = v.findViewById<TextView>(R.id.tvName)
        val tvDateTime = v.findViewById<TextView>(R.id.tvDateTime)
        val rclImage = v.findViewById<RecyclerView>(R.id.rclImage)
    }


}