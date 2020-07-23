package com.example.concung.view.user

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.concung.R
import com.example.concung.model.home.User
import com.example.concung.model.cart.Order
import com.example.concung.model.user.Address
import com.example.concung.util.Utility
import com.example.concung.view.InterfaceClick
import com.example.concung.view.adapter.ListOrderAdapter
import com.example.concung.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.list_order_fragment.*
import java.io.Serializable

class ListOrderFragment : Fragment(), ListOrderAdapter.ListOrder  {
    private var homeViewModel: HomeViewModel? = null
    private var adapter: ListOrderAdapter? = null
    lateinit var back: InterfaceClick.OnBack
    private var id2:Int?=null
    private var type2:Int?=null
    lateinit var fmUser: FragmentManager


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        back = context as InterfaceClick.OnBack
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.list_order_fragment, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(toolbar)
        activity!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity!!.supportActionBar!!.title = resources.getString(R.string.txt_manager_order)

        setHasOptionsMenu(true)

        homeViewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
        fmUser = activity!!.supportFragmentManager

        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rvOrder.layoutManager = layout
        adapter = ListOrderAdapter(context!!,this)
        rvOrder.adapter = adapter

        getOrder()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                back.onBack()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun getOrder() {
        val user = User(Utility.id_user, null)
        homeViewModel!!.getOrder(user).observe(this, Observer { list ->
            progress_bar.visibility = View.GONE
            if (list != null) {
                adapter!!.loadData(list.getData() as ArrayList<Order>)
            }
        })
    }

    private fun dialogAddress(item: Address) {
        val dialog = AlertDialog.Builder(context)
        var dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_address, null)
        dialog.setView(dialogView)

        val alertDialog = dialog.create()
        alertDialog.show()

        val tvName = dialogView.findViewById<TextView>(R.id.tvName)
        val tvAddress = dialogView.findViewById<TextView>(R.id.tvAddress)
        val rbAddress1 = dialogView.findViewById<RadioButton>(R.id.rbAddress1)
        val rbAddress2 = dialogView.findViewById<RadioButton>(R.id.rbAddress2)
        val rgAddress = dialogView.findViewById<RadioGroup>(R.id.rgAddress)
        val tvPhone = dialogView.findViewById<TextView>(R.id.tvPhone)
        val tvConfirm = dialogView.findViewById<TextView>(R.id.tvConfirm)
        val tvTitle = dialogView.findViewById<TextView>(R.id.tvTitle)
        val imCancel = dialogView.findViewById<ImageView>(R.id.imCancel)

        if (item.getId() == null){
            tvTitle.text = resources.getString(R.string.txt_title)
            tvConfirm.text = resources.getString(R.string.txt_insert)
        }


        tvName.text = item.getName()
        tvAddress.text = item.getAddress()
        tvPhone.text = item.getPhone()

        when (item.getType()) {
            1 -> {
                rbAddress1.isChecked = true
            }
            0 -> {
                rbAddress2.isChecked = true
            }
        }

        var typeAddress = item.getType()
        if (typeAddress == 0) {
            rgAddress.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.rbAddress1 -> {
                        typeAddress = 1
                    }
                    R.id.rbAddress2 -> {
                        typeAddress = 0
                    }
                }
            }
        }

        imCancel.setOnClickListener {
            alertDialog.dismiss()
        }


        tvConfirm.setOnClickListener {
            if (tvName.text.toString().isEmpty()){
                tvName.error = resources.getString(R.string.txt_name)
            }else if (tvPhone.text.toString().isEmpty()){
                tvPhone.error = resources.getString(R.string.txt_phone3)
            }else if (tvAddress.text.toString().isEmpty()){
                tvAddress.error = resources.getString(R.string.txt_address3)
            }else {
                val address = Address()
                address.setId(item.getId())
                address.setIdUser(Utility.id_user)
                address.setName(tvName.text.toString())
                address.setPhone(tvPhone.text.toString())
                address.setAddress(tvAddress.text.toString())
                address.setType(typeAddress!!)
                //update id have type 1
                address.setId2(id2)
                address.setType2(type2)

                homeViewModel!!.updateOrInsert(address).observe(this, Observer { item ->
                    if (item != null) {
                        alertDialog.dismiss()
                        getOrder()
                    }
                })
            }
        }
    }
    override fun viewList(order: Order) {
    /*    for (item in list){

        }*/
        val detail = DetailOrderFragment()
        val bundle = Bundle()
        bundle.putSerializable("order",order as Serializable)
        detail.arguments = bundle
        Utility.replaceFragment(fmUser,detail)
    }

}