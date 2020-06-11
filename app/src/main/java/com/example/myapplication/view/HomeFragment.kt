package com.example.myapplication.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.model.Category
import com.example.myapplication.model.RequestId
import com.example.myapplication.util.RxSearch
import com.example.myapplication.util.Utility
import com.example.myapplication.util.Utility.Companion.listNews
import com.example.myapplication.util.Utility.Companion.listProduct
import com.example.myapplication.util.Utility.Companion.listTrademark
import com.example.myapplication.view.adapter.*
import com.example.myapplication.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment(),InterfaceClick.Product {

    private var click :InterfaceClick.home? = null

    fun initFragment( click :InterfaceClick.home){
        this.click = click
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init()
        //all list
        getHome()


    }
    private fun init(){
 /*       RxSearch.fromSearchView(editSearch)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query ->
                }
*/
        menu.setOnClickListener {
            click!!.openMenu()
        }
    }

    @SuppressLint("WrongConstant")
    fun getHome(){
        var list = ArrayList<String>()
        for (i in 0..10){
            list.add("https://9mobi.vn/cf/images/2015/03/nkk/hinh-dep-1.jpg")
        }

        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rcvHome.layoutManager = layout
        val adapter = HomeAdapter(context!!,this)
        rcvHome.adapter = adapter
        rcvHome.isNestedScrollingEnabled = false
        adapter.loadData(Utility.listCategory,listTrademark,listProduct,listNews,list)
    }


    override fun detailProduct(id: String) {
        val intent = Intent(context,InfoProductActivity::class.java)
         intent.putExtra("id",id)
         startActivity(intent)
    }
}