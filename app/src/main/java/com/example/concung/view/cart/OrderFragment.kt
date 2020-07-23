package com.example.concung.view.cart

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.concung.R
import com.example.concung.data.Cart
import com.example.concung.model.home.User
import com.example.concung.model.cart.OrderAddress
import com.example.concung.model.user.Address
import com.example.concung.util.Utility
import com.example.concung.view.InterfaceClick
import com.example.concung.view.adapter.CartAdapter
import com.example.concung.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.order_fragment.*
import kotlinx.android.synthetic.main.order_fragment.rlvCart
import kotlinx.android.synthetic.main.order_fragment.tvDiscount
import kotlinx.android.synthetic.main.order_fragment.tvTotal


class OrderFragment : Fragment(), InterfaceClick.EventCart{
    lateinit var list: List<Cart>
    lateinit var address: Address
    lateinit var homeViewModel: HomeViewModel
    lateinit var adapter: CartAdapter
    var activity_: AppCompatActivity? = null
    private var discount = 0
    lateinit var back: InterfaceClick.OnBack

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        back = context as InterfaceClick.OnBack

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.order_fragment, container, false)
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
        activity_!!.supportActionBar!!.setTitle(resources.getString(R.string.txt_buy))
        setHasOptionsMenu(true)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        list = arguments!!.getParcelableArrayList<Cart>("list") as ArrayList<Cart>

        //
        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rlvCart.layoutManager = layout

        adapter = CartAdapter(context!!, this)
        rlvCart.adapter = adapter

        getCart()
        getAddress()

        btnContinue.setOnClickListener {
            val orderAddress = OrderAddress()
            orderAddress.setIdUser(Utility.id_user)
            orderAddress.setName(address.getName())
            orderAddress.setPhone(address.getPhone())
            orderAddress.setAddress(address.getAddress())
            orderAddress.setDateTime(System.currentTimeMillis())
            orderAddress.setList(list)
            homeViewModel.newOrder(orderAddress).observe(this, Observer {item->
                if (item != null){
                    Toast.makeText(context,"Đặt hàng thành công!",Toast.LENGTH_LONG).show()
                    for (item in list){
                        homeViewModel.deleteCart(item)
                    }
                    back.onBackHome()
                }
            })
        }

    }

   private fun getAddress() {
        val user = User(Utility.id_user, null)
        homeViewModel!!.getAddress(user).observe(this, Observer { list ->

            if (list != null) {

                for (item in list.getData()) {
                    if (item.getType() == 1) {
                        address = item
                        tvName2.text = item.getName()
                        tvAddress2.text = item.getAddress()
                        tvPhone2.text = item.getPhone()
                    }
                }
            }
        })
    }

    /**
     * function get all cart
     */
    fun getCart() {
        if (list.isNotEmpty()) {
            adapter.loadData(list)
            var price = 0
            for (item in list) {
                price += item.price!!.toInt() * item.number!!
            }
            tvPrice.text = Utility.currencyFormatter(price) + resources.getString(R.string.txt_value)
            tvDiscount.text = Utility.currencyFormatter(discount) + resources.getString(R.string.txt_value)
            tvTotal.text = Utility.currencyFormatter(price - discount) + resources.getString(R.string.txt_value)
        } else {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                back.onBack()
            }
            R.id.item_home -> {
                back.onBackHome()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun deleteProduct(cart: Cart) {
        homeViewModel.deleteCart(cart)
        getCart()
    }

}