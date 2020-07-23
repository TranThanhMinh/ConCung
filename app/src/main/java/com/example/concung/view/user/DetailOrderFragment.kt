package com.example.concung.view.user

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.concung.R
import com.example.concung.model.cart.Order
import com.example.concung.model.cart.RequestStatus
import com.example.concung.util.Utility
import com.example.concung.view.InterfaceClick
import com.example.concung.view.adapter.DetailOrderAdapter
import com.example.concung.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.detail_order_online.*
import kotlinx.android.synthetic.main.detail_order_online.toolbar
import kotlinx.android.synthetic.main.detail_order_online.tvAddress
import kotlinx.android.synthetic.main.detail_order_online.tvDiscount
import kotlinx.android.synthetic.main.detail_order_online.tvPrice
import kotlinx.android.synthetic.main.detail_order_online.tvTotal
import kotlinx.android.synthetic.main.item_order.*

class DetailOrderFragment : Fragment() {
    lateinit var back: InterfaceClick.OnBack
    lateinit var order: Order
    var discount = 0
    lateinit var adapter: DetailOrderAdapter
    private var homeViewModel: HomeViewModel? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        back = context as InterfaceClick.OnBack
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_order_online, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(toolbar)
        activity!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity!!.supportActionBar!!.title = resources.getString(R.string.txt_detail_order)

        setHasOptionsMenu(true)

        order = arguments!!.get("order") as Order

        init()
    }

    @SuppressLint("WrongConstant")
    fun init() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        tvCode2.text = order.getIdOrder()
        tvDateTime2.text = Utility.convertStringToDateTime(order.getDateTime()!!)
        tvNumber2.text = order.getListProduct()!!.size.toString()
        tvBuy2.text = "Thanh toán khi nhận hàng"

        tvName.text = "${order.getName()}(${order.getPhone()})"
        tvAddress.text = order.getAddress()

        tvView.visibility = View.GONE

        var price = 0
        for (item in order.getListProduct()!!) {
            price += item.getPrice() * item.getNumber()
        }
        tvTotal2.text = Utility.currencyFormatter(price) + resources.getString(R.string.txt_value)


        tvPrice.text = Utility.currencyFormatter(price) + resources.getString(R.string.txt_value)
        tvDiscount.text = Utility.currencyFormatter(discount) + resources.getString(R.string.txt_value)
        tvTotal.text = Utility.currencyFormatter(price - discount) + resources.getString(R.string.txt_value)

        when (order.getStatus()) {
            0 -> {
                tvStatus.text = getString(R.string.txt_order_canceled)
                tvStatus.setTextColor(Color.RED)
            }
            1 -> {
                tvStatus.text = getString(R.string.txt_order_ok)
                tvStatus.setTextColor(Color.BLACK)
                btnCancel.visibility = View.VISIBLE
                btnCancel.setOnClickListener {
                    updateStatus()
                }
            }
            2 -> {
                tvStatus.text = getString(R.string.txt_order_finished)
                tvStatus.setTextColor(Color.BLUE)
            }
        }

        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rvProduct.layoutManager = layout

        adapter = DetailOrderAdapter(context!!)
        rvProduct.adapter = adapter

        adapter.loadData(order.getListProduct())
    }

    fun updateStatus(){
        val request = RequestStatus()
        request.id_order = order.getIdOrder()
        request.status = 0
        homeViewModel!!.updateStatus(request).observe(this, Observer {item->
            if (item != null){
                Toast.makeText(context, item.getMsg(), Toast.LENGTH_LONG).show()
                tvStatus.text = getString(R.string.txt_order_canceled)
                tvStatus.setTextColor(Color.RED)
                btnCancel.visibility = View.GONE
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                back.onBack()
            }

        }
        return super.onOptionsItemSelected(item)
    }

}