package com.example.myapplication.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.util.Utility.Companion.listCategory
import com.example.myapplication.util.Utility.Companion.listNews
import com.example.myapplication.util.Utility.Companion.listProduct
import com.example.myapplication.util.Utility.Companion.listTrademark
import com.example.myapplication.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.waitting_activity.*


class WaittingActivity : AppCompatActivity() {
    private var homeViewModel: HomeViewModel?=null
    private var login = 4
    private var progressStatus = 0
    private var  progressDrawable: Drawable?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.waitting_activity)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        //list category
        getCategory()
        //list Trademark
        getTrademark()
        //list product
        getProduct()
        //news
        getNews()
        progressDrawable = pb.progressDrawable.mutate()
        progressDrawable!!.setColorFilter(resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN)
        pb.progressDrawable = progressDrawable
    }



    @SuppressLint("WrongConstant")
    fun getCategory(){

        homeViewModel!!.getData().observe(this, Observer {list->
            if(list!=null)
                listCategory = list.getData()
            goConCung()
        })
    }
    @SuppressLint("WrongConstant")
    private fun getTrademark(){
        homeViewModel!!.getTrademark().observe(this, Observer {list->
            if(list!=null)
                listTrademark = list.getData()
            goConCung()
        })
    }

    private fun getProduct(){
        homeViewModel!!.getProduct().observe(this, Observer { list->
            if(list!=null)
                listProduct = list.getData()
            goConCung()
        })
    }


    private fun getNews(){
        homeViewModel!!.getNews().observe(this, Observer { list->
            if(list!=null)
                listNews = list.getData()
            goConCung()
        })
    }

    fun goConCung(){
        pb.progressDrawable = progressDrawable
        login--
        if (login == 0) {
            progressStatus = 100
            pb.progress = progressStatus

            try {
                Thread.sleep(1000)
                startActivity(Intent(this, ConcungActivity::class.java))
                finish()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } else {
            progressStatus += 100/4
            pb.progress = progressStatus
            pb.progressDrawable = progressDrawable
        }
    }

}