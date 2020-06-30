package com.example.myapplication.view.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.ProductWatched
import com.example.myapplication.model.product.Product
import com.example.myapplication.view.InterfaceClick
import com.example.myapplication.view.adapter.ProductWatchedAdapter
import com.example.myapplication.view.product.InfoProductActivity
import com.example.myapplication.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.product_watched_fragment.*
import kotlinx.android.synthetic.main.product_watched_fragment.toolbar

class ProductWatchedFragment : Fragment(), InterfaceClick.EventProduct {
    private var homeViewModel: HomeViewModel? = null
    lateinit var back: InterfaceClick.OnBack
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.product_watched_fragment, container, false)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        back = context as InterfaceClick.OnBack
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(toolbar)
        activity!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity!!.supportActionBar!!.title = resources.getString(R.string.txt_product_view)

        setHasOptionsMenu(true)
        getProduct()
    }

    /**
     * function get all product watched
     */
    private fun getProduct() {
        val layout = GridLayoutManager(context, 2)
        rvProduct.setHasFixedSize(true)
        rvProduct.layoutManager = layout
        val adapter = ProductWatchedAdapter(context!!, this)
        rvProduct.adapter = adapter
        homeViewModel!!.getProductWatched().observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                adapter.loadData(list)
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

    override fun detailProduct(id: Product) {

    }

    override fun detailProductWatch(product: ProductWatched) {
        val intent = Intent(context, InfoProductActivity::class.java)
        intent.putExtra("id", product.id)
        intent.putExtra("love", product.love)
        intent.putExtra("product", "product")

        startActivityForResult(intent,100)
    }
}