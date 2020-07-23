package com.example.concung.view.tablayout

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.concung.R
import com.example.concung.data.ProductWatched
import com.example.concung.model.category.Category
import com.example.concung.model.product.Product
import com.example.concung.util.Utility
import com.example.concung.util.Utility.Companion.listNews
import com.example.concung.util.Utility.Companion.listProduct
import com.example.concung.util.Utility.Companion.listTrademark
import com.example.concung.view.InterfaceClick
import com.example.concung.view.adapter.*
import com.example.concung.view.category.CategoryFragment
import com.example.concung.view.product.InfoProductActivity
import com.example.concung.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment(), InterfaceClick.EventProduct,InterfaceClick.EventCategory {
    private var homeViewModel: HomeViewModel? = null
    private var love: String = "0"
    private var click: IconMenu? = null
    lateinit var fm: FragmentManager


    fun onAttach(click: IconMenu) {
        this.click = click
    }

    interface IconMenu{
        fun setIcon()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return LayoutInflater.from(context).inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //all list
        fm = activity!!.supportFragmentManager
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
        val adapter = HomeAdapter(context!!, this,this)
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
                productWatched.id = product.getId().toString()
                productWatched.name = product.getName()
                productWatched.price = product.getPrice().toString()
                productWatched.image = product.getImage()
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

    override fun gotoCategory(category: Category) {
        click!!.setIcon()
        val categoryFragment = CategoryFragment()
        val  bundle = Bundle()
        bundle.putSerializable("category",category)
        categoryFragment.arguments = bundle
        Utility.replaceHomeFragment(fm, categoryFragment)
    }
}