package com.example.myapplication.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.data.UserFB
import com.example.myapplication.util.Utility.Companion.url
import com.example.myapplication.util.Utility.Companion.id_user
import com.example.myapplication.viewmodel.ConCungViewModel
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.user_fragment.*
import java.util.logging.LogManager

class UserFragment : Fragment() {
    interface goHome{
        fun click()
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
            mHome!!.click()
        }
    }
}