package com.example.myapplication.view.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.Cart
import com.example.myapplication.util.Utility
import com.example.myapplication.view.InterfaceClick
import com.example.myapplication.view.adapter.CartAdapter
import com.example.myapplication.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.cart_activity.*
import kotlinx.android.synthetic.main.cart_activity.toolbar


class CartActivity : AppCompatActivity(), InterfaceClick.EventCart {
    lateinit var list: List<Cart>
    lateinit var homeViewModel: HomeViewModel
    lateinit  var adapter:CartAdapter
    private var discount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_activity)
        init()
    }

    @SuppressLint("WrongConstant")
    fun init() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        //
        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayout.VERTICAL
        rlvCart.layoutManager = layout

       adapter =  CartAdapter(this,this)
        rlvCart.adapter = adapter

        getCart()
    }

    /**
     * function get all cart
     */
    fun getCart(){
        homeViewModel.getCart().observe(this, Observer { list->
            if (list.isNotEmpty()){
                adapter.loadData(list)
                supportActionBar!!.title = getString(R.string.txt_cart) +"(${list.size})"
                var price = 0
                for (item in list){
                    price += item.price!!.toInt() * item.number!!
                }
                tvPrice.text = Utility.currencyFormatter(price)+"đ"
                tvDiscount.text = Utility.currencyFormatter(discount)+"đ"
                tvTotal.text = Utility.currencyFormatter(price-discount)+"đ"
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun deleteProduct(cart: Cart) {
        homeViewModel.deleteCart(cart)
        getCart()
    }
}