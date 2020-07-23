package com.example.concung.view.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.concung.model.comment.Comment

class CommentDiffCallback(var listOld: List<Comment>, var listNew: List<Comment>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listOld[oldItemPosition].getIdComment() == listNew[newItemPosition].getIdComment()
    }

    override fun getOldListSize(): Int {
        return if (listOld != null) listOld.size else 0
    }

    override fun getNewListSize(): Int {
        return if (listNew != null) listNew.size else 0
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listNew[oldItemPosition] == listOld[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}