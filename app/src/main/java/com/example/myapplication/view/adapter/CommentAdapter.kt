package com.example.myapplication.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.comment.Comment
import com.example.myapplication.util.Utility.Companion.convertStringToDateTime


class CommentAdapter(var context: Context, var click: ReplyComment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: List<Comment>

    init {
        list = ArrayList()
    }

    interface ReplyComment {
        fun commentReply(item: Comment)
    }

    fun loadData(list: List<Comment>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list[position]
        (holder as MyViewHolder).tvComment.text = item.getTextComment()
        holder.tvName.text = item.getNameUser()
        holder.tvDateTime.text = convertStringToDateTime(item.getDateTime()!!.toLong())
        holder.tvReply.setOnClickListener {
            click.commentReply(item)
        }

        val adapter = ImageCommentAdapter(context)
        holder.rclImage.adapter = adapter
        adapter.loadData(item.getImage())


        val adapter2 = ReplyCommentAdapter(context)
        holder.rlvReply.adapter = adapter2
        adapter2.loadData(item.getReply())
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))
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
        val tvReply = v.findViewById<RecyclerView>(R.id.tvReply)
        val rlvReply = v.findViewById<RecyclerView>(R.id.rlvReply)

        init {
            val layout = LinearLayoutManager(v.context)
            layout.orientation = LinearLayout.HORIZONTAL
            rclImage.layoutManager = layout

            val layout2 = LinearLayoutManager(v.context)
            layout2.orientation = LinearLayout.HORIZONTAL
            rlvReply.layoutManager = layout2
        }

    }


}