package com.example.myapplication.view.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.user.Address
import com.example.myapplication.view.InterfaceClick
import com.example.myapplication.view.adapter.diffutil.AddressDiffCallback


class AddressAdapter(var context: Context,var show: InterfaceClick.EventAddress) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: ArrayList<Address> = ArrayList()

    private var id2:Int?=null
    private var type2:Int?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_address, parent, false))
    }

    fun loadData(list: ArrayList<Address>) {
        val diffCallback = AddressDiffCallback(list,this.list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list!![position]
        (holder as MyViewHolder).tvName.text = item.getName()
        holder.tvAddress2.text = item.getAddress()
        holder.tvPhone2.text = item.getPhone()
        when (item.getType()) {
            1 -> {
                holder.tvType.isChecked = true
                id2 = item.getId()
                type2 = item.getType()
                show.showAddressDefault(id2!!,type2!!)
            }
            0 -> {
                holder.tvType.isChecked = false
            }
        }


        holder.imEdit.setOnClickListener {
            show.showUpdate(item)
        }

    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvName = v.findViewById<TextView>(R.id.tvName)
        val tvAddress2 = v.findViewById<TextView>(R.id.tvAddress2)
        val tvPhone2 = v.findViewById<TextView>(R.id.tvPhone2)
        val tvType = v.findViewById<CheckBox>(R.id.tvType)
        val imEdit = v.findViewById<ImageView>(R.id.imEdit)
    }

}