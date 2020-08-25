package com.example.concung.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.concung.R


class DistanceAdapter(var context: Context, var listDistance: ArrayList<String>) : BaseAdapter() {
    var view: View? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder? = null
        if (viewHolder == null) {
            viewHolder = ViewHolder()
            view = LayoutInflater.from(context).inflate(R.layout.item_distance, parent, false)
            viewHolder.tvDistance = view!!.findViewById(R.id.tvDistance) as TextView
            view!!.tag = viewHolder
        } else {
            viewHolder = view!!.tag as ViewHolder
        }
        viewHolder.tvDistance!!.text = listDistance[position] +" Km"
        return view!!
    }

    override fun getItem(position: Int): Any {
        return listDistance[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listDistance.size
    }

    //holder pattern
    private class ViewHolder {
        var tvDistance: TextView? = null
    }
}