package com.example.concung.view.adapter


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.concung.R
import com.example.concung.model.cart.Order
import com.example.concung.util.Utility
import com.example.concung.view.adapter.diffutil.OrderDiffCallback
import kotlinx.android.synthetic.main.item_order.*


class ListOrderAdapter(var context: Context,var click: ListOrder) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: ArrayList<Order> = ArrayList()

    interface ListOrder{
        fun viewList(order: Order)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false))
    }

    fun loadData(list: ArrayList<Order>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list!![list.size - position - 1]
        (holder as MyViewHolder).tvCode2.text = item.getIdOrder()
        holder.tvDateTime2.text = Utility.convertStringToDateTime(item.getDateTime()!!)
        holder.tvNumber2.text = item.getListProduct()!!.size.toString()
        holder.tvBuy2.text = "Thanh toán khi nhận hàng"

        var price = 0
        for(item in item.getListProduct()!!){
            price+=item.getPrice() * item.getNumber()
        }
        holder.tvTotal2.text = Utility.currencyFormatter(price) + context.resources.getString(R.string.txt_value)

        holder.tvView.setOnClickListener {
            click.viewList(item)
        }


        when(item.getStatus()){
            0->{
                holder.tvStatus.text = context.getString(R.string.txt_order_canceled)
                holder.tvStatus.setTextColor(Color.RED)
            }
            1->{
                holder.tvStatus.text = context.getString(R.string.txt_order_ok)
                holder.tvStatus.setTextColor(Color.BLACK)
            }
            2->{
                holder.tvStatus.text = context.getString(R.string.txt_order_finished)
                holder.tvStatus.setTextColor(Color.BLUE)
            }
        }

    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvCode2 = v.findViewById<TextView>(R.id.tvCode2)
        val tvDateTime2 = v.findViewById<TextView>(R.id.tvDateTime2)
        val tvNumber2 = v.findViewById<TextView>(R.id.tvNumber2)
        val tvBuy2 = v.findViewById<TextView>(R.id.tvBuy2)
        val tvTotal2 = v.findViewById<TextView>(R.id.tvTotal2)
        val tvView = v.findViewById<TextView>(R.id.tvView)
        val tvStatus = v.findViewById<TextView>(R.id.tvStatus)

    }

}