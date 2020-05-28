package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R
import kotlinx.android.synthetic.main.concung.*

class Concung : AppCompatActivity(),View.OnClickListener {

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


        btnAccount.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       when(v){
           btnAccount ->{
              val intent = Intent(this,AccountActivity::class.java)
               startActivity(intent)
           }
       }
    }
}