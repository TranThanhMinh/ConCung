package com.example.concung.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.concung.R
import com.example.concung.data.ProductWatched
import com.example.concung.model.category.Category
import com.example.concung.model.news.News
import com.example.concung.model.product.Product
import com.example.concung.model.trademark.Trademark
import com.example.concung.util.Utility
import com.example.concung.view.InterfaceClick

class HomeAdapter(var context: Context,var click :InterfaceClick.EventProduct,var click2:InterfaceClick.EventCategory) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), InterfaceClick.EventProduct,InterfaceClick.EventCategory{

    private var listCategory: List<Category> = ArrayList()
    private var listTrademark: List<Trademark> = ArrayList()
    private var listProduct: List<Product> = ArrayList()
    private var listNews: List<News> = ArrayList()
    private var list:ArrayList<String>?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1)
            return MyImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_2, parent, false))
        else {
            return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home, parent, false))
        }
    }

    fun loadData(listCategory: List<Category>, listTrademark: List<Trademark>, listProduct: List<Product>, listNews: List<News>, list:ArrayList<String>) {
        this.listCategory = listCategory
        this.listTrademark = listTrademark
        this.listProduct = listProduct
        this.listNews = listNews
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            0 -> {
                getCategory((holder as MyViewHolder).list_item)
                holder.tvTitle.visibility = View.GONE
                holder.driverLine.visibility = View.GONE
            }
            1 -> {

                val adapter = ImageAdapter(context,list!!)
                (holder as MyImageViewHolder).viewPager.adapter = adapter
                Utility.addBottomDots(holder.layout_dots,list!!.size,0,context)
                holder.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                    }
                    override fun onPageSelected(position: Int) {
                        Utility.addBottomDots(holder.layout_dots,list!!.size,position,context)
                    }

                })
            }
            2 -> {
                getTrademark((holder as MyViewHolder).list_item)
                holder.tvTitle.text = "Mua theo thương hiệu"
            }
            3 -> {
                getProduct((holder as MyViewHolder).list_item)
                holder.tvTitle.text = "Deal online mỗi ngày"
            }
            4 -> {
                getNews((holder as MyViewHolder).list_item)
                holder.tvTitle.text = "Tin tức"
            }
        }

    }

    @SuppressLint("WrongConstant")
    fun getCategory(rlCategory:RecyclerView){
        val layout = GridLayoutManager(context,4)
        layout.orientation = LinearLayout.VERTICAL

        rlCategory.layoutManager = layout
        val adapter = CategoryAdapter(context,this)
        rlCategory.adapter = adapter
        rlCategory.isNestedScrollingEnabled = false
        adapter.loadData(listCategory)
    }
    @SuppressLint("WrongConstant")
    private fun getTrademark(rlcTrademark:RecyclerView){
        val layout = GridLayoutManager(context,2)
        layout.orientation = LinearLayout.HORIZONTAL
        rlcTrademark.setHasFixedSize(true)
        rlcTrademark.layoutManager = layout
        val adapter = TrademarkAdapter(context)
        rlcTrademark.adapter = adapter
        rlcTrademark.isNestedScrollingEnabled = false
        adapter.loadData(listTrademark)
    }

    private fun getProduct(rlProduct:RecyclerView){
        val layout = GridLayoutManager(context,2)
        rlProduct.setHasFixedSize(true)
        rlProduct.layoutManager = layout
        val adapter = ProductAdapter(context,this)
        rlProduct.adapter = adapter
        rlProduct.isNestedScrollingEnabled = false
        adapter.loadData(listProduct)
    }

    @SuppressLint("WrongConstant")
    private fun getNews(rlvNews:RecyclerView){
        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rlvNews.setHasFixedSize(true)

        rlvNews.layoutManager = layout
        val adapter = NewsAdapter(context)
        rlvNews.adapter = adapter
        rlvNews.isNestedScrollingEnabled = false
        adapter.loadData(listNews)
    }

    class MyImageViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val viewPager = v.findViewById<ViewPager>(R.id.viewPager)
        val layout_dots = v.findViewById<LinearLayout>(R.id.layout_dots)
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val list_item = v.findViewById<RecyclerView>(R.id.list_item)
        val tvTitle = v.findViewById<TextView>(R.id.tvTitle)
        val driverLine = v.findViewById<View>(R.id.driverLine)
    }

    override fun detailProduct(product : Product) {
        click.detailProduct(product)
    }


    override fun detailProductWatch(id: ProductWatched) {

    }

    override fun gotoCategory(category: Category) {
        click2.gotoCategory(category)
    }


}