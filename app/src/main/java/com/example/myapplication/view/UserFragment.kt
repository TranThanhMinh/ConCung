package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ConCungViewModel
import kotlinx.android.synthetic.main.user_fragment.*

class UserFragment : Fragment() {
    interface goHome{
        fun logOut()
    }

    private var concung: ConCungViewModel?=null
    var mHome: goHome?=null

    fun init(mHome: goHome){
        this.mHome =mHome
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.user_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        concung = ViewModelProviders.of(this).get(ConCungViewModel::class.java)

        btnLogOut.setOnClickListener {
            mHome!!.logOut()
        }
    }
}