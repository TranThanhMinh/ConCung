package com.example.myapplication.view

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.UserFB
import com.example.myapplication.model.User
import com.example.myapplication.util.Utility.Companion.id_user
import com.example.myapplication.util.Utility.Companion.name_user
import com.example.myapplication.util.Utility.Companion.url
import com.example.myapplication.viewmodel.ConCungViewModel
import com.example.myapplication.viewmodel.LoginViewModel
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.concung.*


class Concung : AppCompatActivity(), View.OnClickListener,UserFragment.goHome,InterfaceClick.home {
    var ft_add: FragmentTransaction?=null
    var fm: FragmentManager? = null
    var concung: ConCungViewModel? = null
    var login: LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.concung)
        concung = ViewModelProviders.of(this).get(ConCungViewModel::class.java)
        login = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        init()
    }

    fun init() {
        fm = supportFragmentManager
        ft_add = fm!!.beginTransaction()

        getUser()
        goHome()

        btnAccount.setOnClickListener(this)
        btnHome.setOnClickListener(this)

    }

    fun getUser(){
        concung!!.getUser().observe(this, androidx.lifecycle.Observer { list ->
            if (list.isNotEmpty()) {
                url = list[0].image
                id_user = list[0].id
            }else {
                url = null
                id_user = null
            }
        })

    }

    // replace fragment home
    fun goHome() {
        ft_add = fm!!.beginTransaction()
        val home = HomeFragment()
        home.initFragment(this)
        ft_add!!.replace(R.id.fgLayout,home)
        ft_add!!.commit()
    }

    override fun onClick(v: View?) {
        when (v) {
            btnAccount -> {
                if (id_user != null) {
                    ft_add = fm!!.beginTransaction()
                    val userFragment = UserFragment()
                    userFragment.init(this)
                    ft_add!!.replace(R.id.fgLayout, userFragment)
                    ft_add!!.commit()
                } else {
                    val intent = Intent(this, LoginAccountActivity::class.java)
                    startActivityForResult(intent, 1)
                }
            }

            btnHome -> {
                goHome()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == 2) {
            url = data!!.getSerializableExtra("image") as String
            id_user = data!!.getSerializableExtra("id") as String
            name_user = data!!.getSerializableExtra("name_user") as String
         /*   val user = User(id_user,"123456");
            login!!.login(user)*/
        }
    }

    override fun click() {
        val userFB = UserFB()
        userFB.id = id_user
        userFB.image = url
        concung!!.deleteUser(userFB)

        FacebookSdk.sdkInitialize(this);
        LoginManager.getInstance().logOut();

        getUser()
        goHome()
    }

    override fun openMenu() {
        drawerLayout.openDrawer(navigationView)
    }
}