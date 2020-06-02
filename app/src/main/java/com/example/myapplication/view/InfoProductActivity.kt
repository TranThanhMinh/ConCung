package com.example.myapplication.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.model.RequestId
import com.example.myapplication.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_product_activity.*

class InfoProductActivity : AppCompatActivity(){
    private var homeViewModel:HomeViewModel?=null
    private var id:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_product_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);

        //init
        init()
    }

    fun init(){
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        id = intent.getSerializableExtra("id") as String
        getIdProduct(id)

    }

    private fun getIdProduct(id:String?){
        val body = RequestId()
        body.id = id
        homeViewModel!!.getIdProduct(body).observe(this, Observer { list->
            progress_bar.visibility = View.GONE
            if(list!=null){
                Picasso.with(this).load(list.getData()[0].getImage()).into(imageView)
                tvName.text = list.getData()[0].getName()
            }else {
                tvName.text = "không có thông tin"
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                Log.e("Minh","back kaka")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}