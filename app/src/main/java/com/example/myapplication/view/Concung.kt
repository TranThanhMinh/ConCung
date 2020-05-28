package com.example.myapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R
import com.example.myapplication.util.RxSearch
import kotlinx.android.synthetic.main.concung.*
import kotlinx.android.synthetic.main.home_fragment.*
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class Concung : AppCompatActivity() {
  var  fm:FragmentManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.concung)
        init()

    }

    fun init() {
        fm = supportFragmentManager
        val ft_add: FragmentTransaction = fm!!.beginTransaction()
        ft_add.replace(R.id.fgLayout, HomeFragment())
        ft_add.commit()
    }
}