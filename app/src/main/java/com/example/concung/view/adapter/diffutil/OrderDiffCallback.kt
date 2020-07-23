package com.example.concung.view.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.concung.model.cart.Order

class OrderDiffCallback(var listOld:List<Order>, var listNew:List<Order>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listOld[oldItemPosition].getIdOrder() == listNew[oldItemPosition].getIdOrder()
    }

    override fun getOldListSize(): Int {
        return  listOld.size
    }

    override fun getNewListSize(): Int {
        return  listNew.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listOld[oldItemPosition] == listNew[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}