package com.example.concung.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.concung.R
import com.example.concung.model.comment.Comment
import com.example.concung.util.Utility.Companion.convertStringToDateTime
import com.example.concung.view.adapter.diffutil.CommentDiffCallback



class CommentAdapter(var context: Context, var click: ReplyComment) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),ReplyCommentAdapter.ReplyComment {

    var list: ArrayList<Comment> = ArrayList()

    interface ReplyComment {
        fun commentReply(item: String)
    }

    fun loadData(list: ArrayList<Comment>) {
        val diffCallback = CommentDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)

    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list[position]
        (holder as MyViewHolder).tvComment.text = item.getTextComment()
        holder.tvName.text = item.getNameUser()
        holder.tvDateTime.text = convertStringToDateTime(item.getDateTime()!!.toLong())
        holder.tvReply.setOnClickListener {
            click.commentReply(item.getIdComment()!!)
        }

        val adapter = ImageCommentAdapter(context)
        holder.rclImage.adapter = adapter
        adapter.loadData(item.getImage())


        val adapter2 = ReplyCommentAdapter(context,this)
        holder.rlvReply.adapter = adapter2
        adapter2.loadData(item.getReply())
        holder.rlvReply.isNestedScrollingEnabled = false
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
        val tvReply = v.findViewById<TextView>(R.id.tvReply)
        val rclImage = v.findViewById<RecyclerView>(R.id.rclImage)
        val rlvReply = v.findViewById<RecyclerView>(R.id.rlvReply)

        init {
            val layout = LinearLayoutManager(v.context)
            layout.orientation = LinearLayout.HORIZONTAL
            rclImage.layoutManager = layout

            val layout2 = LinearLayoutManager(v.context)
            layout2.orientation = LinearLayout.VERTICAL
            rlvReply.layoutManager = layout2
        }

    }

    override fun commentReply(item: String) {
        click.commentReply(item)
    }


}