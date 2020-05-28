package com.example.myapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Student
import com.example.myapplication.view.InterfaceClick


class AdapterStudent(context:Context,click: InterfaceClick.Adapter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list:List<Student>
    private var context:Context = context
    private var click:InterfaceClick.Adapter = click
    init {
        list = ArrayList()
    }

    fun loadData(list:List<Student>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {

            }
            else -> {

                var item = list[position - 1]
                (holder as MyViewHolder).tvId.text = item.mIdStudent.toString()
                holder.tvName.text = item.mNameStudent
                holder.tvClass.text = item.mClass
                holder.tvNumber.text = item.mNumber.toString()
                when (item.check) {
                    true -> {
                        click.clickChooseDelete(item.check)
                        setBackground(holder, R.color.colorPrimaryDark, R.color.colorWhite)
                    }
                    false -> setBackground(holder,R.color.colorWhite,R.color.colorBlack)
                }

                holder.cbChoose.isChecked = item.viewItem

                holder.ctlLayout.setOnClickListener {
                    item.check = !item.check
                    click.clickChooseDelete(false)
                    notifyDataSetChanged()
                }

                holder.cbChoose.setOnClickListener {
                    for (Student in list){
                        if(item.mIdStudent == Student.mIdStudent){
                            Student.viewItem = !item.viewItem
                        }else Student.viewItem = false
                    }
                    notifyDataSetChanged()
                    click.clickItem(item)
                }
            }
        }

    }

    fun setBackground(holder:MyViewHolder, idColor:Int, textColor:Int){
        holder.ctlLayout.setBackgroundColor(ContextCompat.getColor(context,idColor))
        holder.tvId.setTextColor(ContextCompat.getColor(context,textColor))
        holder.tvName.setTextColor(ContextCompat.getColor(context,textColor))
        holder.tvClass.setTextColor(ContextCompat.getColor(context,textColor))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0)
            return TitleMyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_title_student, parent, false))
        else return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false))
    }

    override fun getItemCount(): Int {
       return list.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

   class MyViewHolder(v:View) : RecyclerView.ViewHolder(v) {
       val tvId = v.findViewById<TextView>(R.id.tvId)
       val tvName = v.findViewById<TextView>(R.id.tvName)
       val tvClass = v.findViewById<TextView>(R.id.tvClass)
       val tvNumber = v.findViewById<TextView>(R.id.tvNumber)
       val ctlLayout = v.findViewById<ConstraintLayout>(R.id.ctlLayout)
       val cbChoose = v.findViewById<CheckBox>(R.id.cbChoose)
    }

    class TitleMyViewHolder(v:View) : RecyclerView.ViewHolder(v) {
        val tvId = v.findViewById<TextView>(R.id.tvId)
        val tvName = v.findViewById<TextView>(R.id.tvName)
        val tvClass = v.findViewById<TextView>(R.id.tvClass)
        val ctlLayout = v.findViewById<ConstraintLayout>(R.id.ctlLayout)

    }
}