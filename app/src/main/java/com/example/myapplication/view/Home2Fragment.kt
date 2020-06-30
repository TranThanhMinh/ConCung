package com.example.myapplication.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.ProductWatched
import com.example.myapplication.model.product.Product
import com.example.myapplication.util.Utility
import com.example.myapplication.util.Utility.Companion.listNews
import com.example.myapplication.util.Utility.Companion.listProduct
import com.example.myapplication.util.Utility.Companion.listTrademark
import com.example.myapplication.view.adapter.*
import com.example.myapplication.view.product.InfoProductActivity
import com.example.myapplication.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment_2.*


class Home2Fragment : Fragment(), InterfaceClick.EventProduct {
    private var homeViewModel: HomeViewModel? = null
    private var love: String = "0"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return LayoutInflater.from(context).inflate(R.layout.home_fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //all list
        getHome()


    }

    @SuppressLint("WrongConstant")
    fun getHome() {
        var list = ArrayList<String>()
        for (i in 0..10) {
            list.add("https://9mobi.vn/cf/images/2015/03/nkk/hinh-dep-1.jpg")
        }

        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rcvHome.layoutManager = layout
        val adapter = HomeAdapter(context!!, this)
        rcvHome.adapter = adapter
        rcvHome.isNestedScrollingEnabled = false
        adapter.loadData(Utility.listCategory, listTrademark, listProduct, listNews, list)
    }


    override fun detailProduct(product: Product) {
        Log.e("MInh2", "isNotEmpty")
        homeViewModel!!.productExist(product.getId().toString()).observe(viewLifecycleOwner, Observer {
            list ->
            if(viewLifecycleOwner.lifecycle.currentState== Lifecycle.State.RESUMED) {
                val productWatched = ProductWatched()
                productWatched.id = product!!.getId().toString()
                productWatched.name = product!!.getName()
                productWatched.price = product!!.getPrice().toString()
                productWatched.image = product!!.getImage()
                if (list.isNotEmpty()) {
                    Log.e("MInh2", "isNotEmpty")
                    love = list[0].love
                    productWatched.love = love
                    homeViewModel!!.updateProduct(productWatched)
                } else {

                    productWatched.love = love
                    homeViewModel!!.insertProduct(productWatched)
                    Log.e("MInh2", "isEmpty")
                }

                val intent = Intent(context, InfoProductActivity::class.java)
                intent.putExtra("id", product.getId().toString())
                intent.putExtra("love",love)
                intent.putExtra("type","product")
                startActivityForResult(intent,100)
            }
        })


    }

    override fun detailProductWatch(id: ProductWatched) {

    }
}