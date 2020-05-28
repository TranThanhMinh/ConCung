package com.example.myapplication.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.model.Category
import com.example.myapplication.util.RxSearch
import com.example.myapplication.view.adapter.CategoryAdapter
import com.example.myapplication.view.adapter.ImageAdapter
import com.example.myapplication.view.adapter.ProductAdapter
import com.example.myapplication.view.adapter.TrademarkAdapter
import com.example.myapplication.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {
    private var homeViewModel:HomeViewModel?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        init()
        //list category
        getCategory()
        //list image
        getViewPager()
        //list Trademark
        getTrademark()
        //list product
        getProduct()
    }
    private fun init(){
        RxSearch.fromSearchView(editSearch)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query ->
                }
    }

    @SuppressLint("WrongConstant")
    fun getCategory(){
        val layout = GridLayoutManager(context,4)
        layout.orientation = LinearLayout.VERTICAL
        rlCategory.layoutManager = layout
        val adapter = CategoryAdapter(context!!)
        rlCategory.adapter = adapter
        homeViewModel!!.getData().observe(this, Observer {list->
            if(list!=null)
            adapter.loadData(list.getData())
        })
    }
    @SuppressLint("WrongConstant")
    private fun getTrademark(){
        val layout = GridLayoutManager(context,2)
        layout.orientation = LinearLayout.VERTICAL
        rlcTrademark.layoutManager = layout
        val adapter = TrademarkAdapter(context!!)
        rlcTrademark.adapter = adapter
        homeViewModel!!.getData().observe(this, Observer {list->
            if(list!=null)
            adapter.loadData(list.getData())
        })
    }

   private fun getProduct(){
        val layout = GridLayoutManager(context,2)
       rlProduct.layoutManager = layout
        val adapter = ProductAdapter(context!!)
        rlProduct.adapter = adapter
        homeViewModel!!.getProduct().observe(this, Observer { list->
            if(list!=null)
                adapter.loadData(list.getData())
        })
    }


    private fun getViewPager(){
        var list = ArrayList<String>()
        for (i in 0..10){
            list.add("https://9mobi.vn/cf/images/2015/03/nkk/hinh-dep-1.jpg")
        }
        val adapter = ImageAdapter(context!!,list)
        viewPager.adapter = adapter
        addBottomDots(list.size,0)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                addBottomDots(list.size,position)
            }

        })

    }
    private fun addBottomDots( size: Int, current: Int) {
        val dots: Array<ImageView?> = arrayOfNulls<ImageView>(size)
        layout_dots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(context)
            val width_height = 20
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(width_height, width_height))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle_outline)
            layout_dots.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[current]!!.setImageResource(R.drawable.shape_circle)
        }
    }
}