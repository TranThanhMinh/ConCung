package com.example.myapplication.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.data.UserFB
import com.example.myapplication.util.Utility.Companion.id_user
import com.example.myapplication.util.Utility.Companion.name_user
import com.example.myapplication.util.Utility.Companion.url
import com.example.myapplication.view.login.LoginAccountActivity
import com.example.myapplication.viewmodel.ConCungViewModel
import com.example.myapplication.viewmodel.LoginViewModel
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.concung.*


class Concung : AppCompatActivity(), View.OnClickListener,UserFragment.goHome,InterfaceClick.home {
    private  var ft_add: FragmentTransaction?=null
    private  var fm: FragmentManager? = null
    private var concung: ConCungViewModel? = null
    private var login: LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.concung)
        concung = ViewModelProviders.of(this).get(ConCungViewModel::class.java)
        login = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        init()
    }

    private fun init() {
        fm = supportFragmentManager
        ft_add = fm!!.beginTransaction()

        getUser()
        goHome()

        btnAccount.setOnClickListener(this)
        btnHome.setOnClickListener(this)

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("TAG", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token
                    // Log and toast
                    Log.d("TAG", token)

                })

    }

    override fun onStart() {
        super.onStart()
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener { task ->
                    var msg = "that bai"
                    if (task.isSuccessful) {
                        msg = "thanh cong"
                    }
                    Log.d("TAG", msg)
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                }
        // [END subscribe_topics]
    }

    override fun onDestroy() {
        super.onDestroy()
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().unsubscribeFromTopic("news");
        // [END subscribe_topics]
    }

    /**
     * function take information of user login
     */
    private fun getUser(){
        concung!!.getUser().observe(this, androidx.lifecycle.Observer { list ->
            if (list.isNotEmpty()) {
                url = list[0].image
                id_user = list[0].id
                name_user = list[0].name_user
            }else {
                url = null
                id_user = null
                name_user = null
            }
        })

    }

    /**
     * function move to Home
     */
   private fun goHome() {
        setTextColor()
        tvAccount.setTextColor(resources.getColor(R.color.colorAccent))

        ft_add = fm!!.beginTransaction()
        val home = HomeFragment()
        home.initFragment(this)
        ft_add!!.replace(R.id.fgLayout,home)
        ft_add!!.commit()
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {
        when (v) {
            btnAccount -> {
                if (id_user != null) {
                    ft_add = fm!!.beginTransaction()
                    val userFragment = UserFragment()
                    userFragment.init(this)
                    ft_add!!.replace(R.id.fgLayout, userFragment)
                    ft_add!!.commit()
                    setTextColor()
                    tvAccount.setTextColor(resources.getColor(R.color.colorAccent))
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

    /**
     * function set text color
     */
    @SuppressLint("ResourceAsColor")
    private  fun setTextColor(){
        tvHome.setTextColor(resources.getColor(R.color.colorBlack3))
        tvAccount.setTextColor(resources.getColor(R.color.colorBlack3))
        tvVip.setTextColor(resources.getColor(R.color.colorBlack3))
        tvNotifi.setTextColor(resources.getColor(R.color.colorBlack3))
        tvSearch.setTextColor(resources.getColor(R.color.colorBlack3))

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

    override fun logOut() {
        val userFB = UserFB()
        userFB.id = id_user
        userFB.image = url
        concung!!.deleteUser(userFB)

        FacebookSdk.sdkInitialize(this)
        LoginManager.getInstance().logOut();

        getUser()
        goHome()
    }

    override fun openMenu() {
        drawerLayout.openDrawer(navigationView)
    }
}