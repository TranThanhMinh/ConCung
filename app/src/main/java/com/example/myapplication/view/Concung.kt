package com.example.myapplication.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.dagger.Component.DaggerUserComponent
import com.example.myapplication.dagger.Component.UserComponent
import com.example.myapplication.data.UserFB
import com.example.myapplication.model.Policy
import com.example.myapplication.util.Utility
import com.example.myapplication.util.Utility.Companion.id_user
import com.example.myapplication.util.Utility.Companion.image_user
import com.example.myapplication.util.Utility.Companion.name_user
import com.example.myapplication.util.Utility.Companion.url
import com.example.myapplication.view.adapter.PolicyAdapter
import com.example.myapplication.view.login.LoginAccountActivity
import com.example.myapplication.view.menu.PolicyFragment
import com.example.myapplication.view.user.AddressFragment
import com.example.myapplication.viewmodel.ConCungViewModel
import com.example.myapplication.viewmodel.LoginViewModel
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.concung.*
import javax.inject.Inject


class Concung : AppCompatActivity(), View.OnClickListener, UserFragment.goHome, InterfaceClick.home,PolicyAdapter.ViewWeb,InterfaceClick.OnBack{
    lateinit var fm: FragmentManager
    private var concung: ConCungViewModel? = null
    private var login: LoginViewModel? = null
    var userComponent: UserComponent? = null
    var page = false

    @Inject
    lateinit var userFB: UserFB

    @Inject
    lateinit var policy: Policy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.concung)
        concung = ViewModelProviders.of(this).get(ConCungViewModel::class.java)
        login = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        init()
    }

    private fun init() {
       /* tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.home)).setIcon(R.drawable.ic_home))
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.account)).setIcon(R.drawable.ic_account))
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.vip)).setIcon(R.drawable.ic_vip))
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.notifi)).setIcon(R.drawable.ic_notification))
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.location)).setIcon(R.drawable.ic_location))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
              when(p0!!.position){
                  0->{
                     goHome()
                  }
                  1->{
                      goUser()
                  }
              }
            }

        })*/


        fm = supportFragmentManager

        userComponent = DaggerUserComponent.builder().build()
        userComponent!!.inject(this)

        checkPermission()

        getUser()
        goHome()
        getPolicy()
        btnAccount.setOnClickListener(this)
        btnHome.setOnClickListener(this)
    /*    menu.setOnClickListener(this)
        cart.setOnClickListener(this)*/
        rlUser.setOnClickListener(this)
        rlCall.setOnClickListener(this)
        rlCall2.setOnClickListener(this)

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

    fun checkPermission() {
        // Với Android Level >= 23 bạn phải hỏi người dùng cho phép ghi dữ liệu vào thiết bị.
        if (Build.VERSION.SDK_INT >= 23) {
            // Kiểm tra quyền đọc/ghi dữ liệu vào thiết bị lưu trữ ngoài.
            val cameraPermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
            val callPermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE)
            val readPermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            val writePermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (writePermission != PackageManager.PERMISSION_GRANTED || callPermission != PackageManager.PERMISSION_GRANTED ||
                    readPermission != PackageManager.PERMISSION_GRANTED || cameraPermission != PackageManager.PERMISSION_GRANTED) {

                // Nếu không có quyền, cần nhắc người dùng cho phép.
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE ),
                        1
                )
            }
        }
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
                }
        // [END subscribe_topics]
    }

    override fun onDestroy() {
        super.onDestroy()
        // [START subscribe_topics]
        //   FirebaseMessaging.getInstance().unsubscribeFromTopic("news");
        // [END subscribe_topics]
    }

    /**
     * function take information of user login
     */
    private fun getUser() {
        concung!!.getUser().observe(this, androidx.lifecycle.Observer { list ->
            if (list.isNotEmpty()) {
                url = list[0].image
                id_user = list[0].id
                name_user = list[0].name_user
                image_user = list[0].image_user
            } else {
                url = null
                id_user = null
                name_user = null
                image_user = null
            }
            setInformationUser()
        })

    }

    /**
     * function get list policy
     */
    @SuppressLint("WrongConstant")
    fun getPolicy() {
        val list = ArrayList<Policy>()

       val policy =  Policy()
        policy.name = "Giới thiệu về Concung.com"
        policy.url = "https://concung.com/gioi-thieu.html"
        list.add(policy)

        val policy2 =  Policy()
        policy2.name = "Mua & Giao nhận Online"
        policy2.url = "https://concung.com/chinh-sach-giao-hang.html"
        list.add(policy2)

        val policy3 =  Policy()
        policy3.name = "Qui định & hình thức thanh toán"
        policy3.url = "https://concung.com/hinh-thuc-thanh-toan.html"
        list.add(policy3)

        val policy4 =  Policy()
        policy4.name = "Bảo hành & Bảo trì"
        policy4.url = "https://concung.com/chinh-sach-bao-hanh.html"
        list.add(policy4)

        val policy5 =  Policy()
        policy5.name = "Đổi trả & Hoàn tiền"
        policy5.url = "https://concung.com/chinh-sach-doi-tra-hang.html"
        list.add(policy5)

        val policy6 =  Policy()
        policy6.name = "Chính sách bảo mật"
        policy6.url = "https://concung.com/chinh-sach-bao-mat-thong-tin.html"
        list.add(policy6)

        val policy7 =  Policy()
        policy7.name = "Chính sách & Quy định chung"
        policy7.url = "https://concung.com/dieu-khoan-su-dung.html"
        list.add(policy7)

        val layout =LinearLayoutManager(this)
        layout.orientation = LinearLayout.VERTICAL
        rlvConCung.layoutManager = layout
       val adapter = PolicyAdapter(this,this)
        rlvConCung.adapter = adapter

        adapter.loadData(list)
    }

    /**
     * function move to Home
     */
    private fun goHome() {
        setTextColor()
        tvHome.setTextColor(resources.getColor(R.color.colorAccent))

        val home = HomeFragment()
        home.initClick(this)
        Utility.replaceFragment(fm!!,home)
    }

    /**
     * function move to layout User
     */
    private fun goUser(){
        if (id_user != null) {
            val userFragment = UserFragment()
            userFragment.init(this)
            Utility.replaceFragment(fm!!,userFragment)
            setTextColor()
            tvAccount.setTextColor(resources.getColor(R.color.colorAccent))
        } else {
            val intent = Intent(this, LoginAccountActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {
        when (v) {
            btnAccount -> {
                goUser()
            }

            btnHome -> {
                goHome()
            }

           /* menu->{
                when(page){
                    true->{
                        page = !page
                        goHome()
                    }
                    false->{
                        openMenu()
                    }
                }

            }
            cart->{
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }*/
            rlUser->{
                closeMenu()
                goUser()
            }
            rlCall->{
                callPhone(resources.getString(R.string.txt_phone_1))
            }

            rlCall2->{
                callPhone(resources.getString(R.string.txt_phone_2))
            }
        }
    }

    /**
     * function set text color
     */
    @SuppressLint("ResourceAsColor")
    private fun setTextColor() {
        tvHome.setTextColor(resources.getColor(R.color.colorBlack3))
        tvAccount.setTextColor(resources.getColor(R.color.colorBlack3))
        tvVip.setTextColor(resources.getColor(R.color.colorBlack3))
        tvNotifi.setTextColor(resources.getColor(R.color.colorBlack3))
        tvSearch.setTextColor(resources.getColor(R.color.colorBlack3))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == 2) {// login
            url = data!!.getSerializableExtra("image") as String
            id_user = data!!.getSerializableExtra("id") as String
            name_user = data!!.getSerializableExtra("name_user") as String
            image_user = data!!.getSerializableExtra("image") as String
            setInformationUser()
        }
        else  if (resultCode == 100) { //click home on toolbar
            goHome()
        }
    }

    /**
     * function set information user
     */
    private fun setInformationUser(){
        if(name_user != null) {
            Picasso.with(this).load(image_user).error(R.drawable.ic_user).into(imUser)
            tvName1.text = name_user
            tvName2.text = resources.getString(R.string.txt_manager)
        }else {
            Picasso.with(this).load(R.drawable.ic_user).into(imUser)
            tvName1.text = resources.getString(R.string.txt_login)
            tvName2.text = resources.getString(R.string.txt_logout)
        }
    }

    override fun logOut() {

        userFB!!.id = id_user
        userFB!!.image = url
        concung!!.deleteUser(userFB!!)

        FacebookSdk.sdkInitialize(this)
        LoginManager.getInstance().logOut();

        getUser()
        goHome()
    }

    override fun openMenu() {
        when(page){
            true->{
                page = !page
                goHome()
            }
            false->{
                drawerLayout.openDrawer(navigationView)
            }
        }

    }

    override fun closeMenu() {
        drawerLayout.closeDrawer(navigationView)
    }


    override fun show(url: String) {
        closeMenu()
        try {
            Thread.sleep(500)
            page = !page
          //  menu.setImageDrawable(resources.getDrawable(R.drawable.ic_back))
            val home = PolicyFragment()
            val bundle = Bundle()
            bundle.putString("url", url)
            home.arguments = bundle
            Utility.replaceFragment(fm!!,home)
        }catch (ex:Exception){
            ex.printStackTrace()
        }

    }

   private fun callPhone(phone:String){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        startActivity(intent)
    }

    override fun onBack() {
        onBackPressed()
    }

    override fun onBackHome() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}