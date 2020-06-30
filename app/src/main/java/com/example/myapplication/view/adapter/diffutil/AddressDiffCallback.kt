package com.example.myapplication.view.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.model.user.Address

class AddressDiffCallback(var listOld:List<Address>,var listNew:List<Address>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listOld[oldItemPosition].getId() == listNew[oldItemPosition].getId()
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