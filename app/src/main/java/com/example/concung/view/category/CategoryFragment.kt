package com.example.concung.view.category

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.concung.R
import com.example.concung.data.ProductWatched
import com.example.concung.model.category.Category
import com.example.concung.model.home.RequestId
import com.example.concung.model.product.Product
import com.example.concung.util.Utility
import com.example.concung.view.InterfaceClick
import com.example.concung.view.adapter.CategoryAdapter
import com.example.concung.view.adapter.ImageAdapter
import com.example.concung.view.adapter.ProductAdapter
import com.example.concung.view.product.InfoProductActivity
import com.example.concung.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.category_fragment.*
import kotlinx.android.synthetic.main.item_home_2.*

class CategoryFragment : Fragment(), InterfaceClick.EventCategory,InterfaceClick.EventProduct {
    var homeViewModel: HomeViewModel? = null
    private var love: String = "0"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.category_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        val category = arguments!!.getSerializable("category") as Category
        tvTitle.text = category.getNameCategory()

        val id = RequestId()
        id.id = category.getIdCategory().toString()
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        homeViewModel!!.getCategory(id).observe(this, Observer { list ->
            hideProgressbar()
            if (list != null){
                getCategory(list.getCategory())
                getProduct(list.getProduct())
            }

        })

        var list = ArrayList<String>()
        for (i in 0..10) {
            list.add("https://9mobi.vn/cf/images/2015/03/nkk/hinh-dep-1.jpg")
        }
        val adapter = ImageAdapter(context!!, list!!)
        viewPager.adapter = adapter
        Utility.addBottomDots(layout_dots, list!!.size, 0, context!!)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                Utility.addBottomDots(layout_dots, list!!.size, position, context!!)
            }

        })
    }

    private fun hideProgressbar() {
            progress_bar.visibility = View.GONE
    }


    @SuppressLint("WrongConstant")
    fun getCategory(listCategory: List<Category>) {
        val layout = GridLayoutManager(context, 4)
        layout.orientation = LinearLayout.VERTICAL

        rvCategory.layoutManager = layout
        val adapter = CategoryAdapter(context!!, this)
        rvCategory.adapter = adapter
        rvCategory.isNestedScrollingEnabled = false
        adapter.loadData(listCategory)
    }

    private fun getProduct(listProduct:List<Product>){
        val layout = GridLayoutManager(context,2)
        rvProduct.setHasFixedSize(true)
        rvProduct.layoutManager = layout
        val adapter = ProductAdapter(context!!,this)
        rvProduct.adapter = adapter
        rvProduct.isNestedScrollingEnabled = false
        adapter.loadData(listProduct)
    }

    override fun gotoCategory(category: Category) {

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
}