package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.model.Example
import com.example.myapplication.util.Utility
import com.example.myapplication.viewmodel.ExampleViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
    }

    fun initLayout(){
       val mainFragment: FragmentTransaction = supportFragmentManager.beginTransaction()
        mainFragment.replace(R.id.fgLayout,MainFragment())
        mainFragment.commit()
        tvName.text = Utility.getInfo(this).userName
        btnLogOut.setOnClickListener {
            Utility.deleteUser(this)
            finish()
        }
    }
}
