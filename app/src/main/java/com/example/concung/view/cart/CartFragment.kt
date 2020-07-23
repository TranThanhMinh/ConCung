package com.example.concung.view.cart

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.concung.R
import com.example.concung.data.Cart
import com.example.concung.util.Utility
import com.example.concung.view.InterfaceClick
import com.example.concung.view.adapter.CartAdapter
import com.example.concung.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.cart_activity.*
import kotlinx.android.synthetic.main.cart_activity.toolbar
import kotlinx.android.synthetic.main.cart_activity.tvPrice



class CartFragment : Fragment(), InterfaceClick.EventCart {
    lateinit var list: ArrayList<Cart>
    lateinit var homeViewModel: HomeViewModel
    lateinit  var adapter:CartAdapter
    lateinit var fm:FragmentManager
      var activity_ : AppCompatActivity?=null
    private var discount = 0
    lateinit var back: InterfaceClick.OnBack

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        back = context as InterfaceClick.OnBack

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cart_activity,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @SuppressLint("WrongConstant")
    fun init() {
        activity_ = activity as AppCompatActivity
        activity_!!.setSupportActionBar(toolbar)
        activity_!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        fm = activity_!!.supportFragmentManager

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        //
        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rlvCart.layoutManager = layout

       adapter =  CartAdapter(context!!,this)
        rlvCart.adapter = adapter

        getCart()

        btnContinue2.setOnClickListener {
            back.onBack()
        }

        btnContinue.setOnClickListener {
            val orderFragment = OrderFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("list",list)
            orderFragment.arguments = bundle
            Utility.replaceProductFragment(fm,orderFragment)
        }
    }

    /**
     * function get all cart
     */
    fun getCart(){
        homeViewModel.getCart().observe(this, Observer { list->
            if (list.isNotEmpty()){
                this.list = list as ArrayList<Cart>
                nslProduct.visibility = View.VISIBLE
                llNoProduct.visibility = View.GONE
                adapter.loadData(list)
                activity_!!.supportActionBar!!.title = getString(R.string.txt_cart) +"(${list.size})"
                var price = 0
                for (item in list){
                    price += item.price!!.toInt() * item.number!!
                }
                tvPrice.text = Utility.currencyFormatter(price)+resources.getString(R.string.txt_value)
                tvDiscount.text = Utility.currencyFormatter(discount)+resources.getString(R.string.txt_value)
                tvTotal.text = Utility.currencyFormatter(price-discount)+resources.getString(R.string.txt_value)
            }else{
                nslProduct.visibility = View.GONE
                llNoProduct.visibility = View.VISIBLE
                activity_!!.supportActionBar!!.title = getString(R.string.txt_cart)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                back.onBack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun deleteProduct(cart: Cart) {
        homeViewModel.deleteCart(cart)
        getCart()
    }
}