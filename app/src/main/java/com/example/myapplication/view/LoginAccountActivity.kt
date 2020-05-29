package com.example.myapplication.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.UserFB
import com.example.myapplication.util.Utility.Companion.saveUserId
import com.example.myapplication.viewmodel.ConCungViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.account_activity.*
import java.io.File
import java.security.MessageDigest


class LoginAccountActivity:AppCompatActivity(),View.OnClickListener {
    var callbackManager: CallbackManager?=null
    var concung: ConCungViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)
        concung = ViewModelProviders.of(this).get(ConCungViewModel::class.java)
        init()
    }

    fun init(){
     /*   try {
            val packageInfo = packageManager.getPackageInfo("com.example.myapplication", PackageManager.GET_SIGNATURES)
            for (signature in packageInfo.signatures) {
                val messageDigest: MessageDigest = MessageDigest.getInstance("SHA")
                messageDigest.update(signature.toByteArray())
                Log.e("KeyHash", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT))
            }
        } catch (e: Exception) {
        }*/
        FacebookSdk.sdkInitialize(this)
        imCancel.setOnClickListener(this)
        login_button.setOnClickListener(this)

        callbackManager = CallbackManager.Factory.create();
    }

    fun showToast(text:String,url:String){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show()
        Glide.with(this).load(url).error(R.drawable.ic_launcher_background).into(imUser)

    }

    override fun onClick(v: View?) {
      when(v){
          imCancel->{
              setResult(3, Intent())
              finish()
          }
          login_button->{
              LoginManager.getInstance().registerCallback(callbackManager,
                      object : FacebookCallback<LoginResult?> {
                          override fun onSuccess(loginResult: LoginResult?) {
                              // App code
                              val  imageURL = "https://graph.facebook.com/"+loginResult!!.accessToken.userId+"/picture?type=normal";
                              val userFB = UserFB()
                              userFB.id = loginResult!!.accessToken.userId
                              userFB.image = imageURL
                              concung!!.insert(userFB)

                              val intent = Intent()
                              intent.putExtra("id",userFB.id)
                              intent.putExtra("image",userFB.image)
                              setResult(2,intent)
                              finish()
                          }

                          override fun onCancel() {
                              showToast("onCancel","")
                              // App code
                          }

                          override fun onError(exception: FacebookException) {
                              // App code
                              showToast("onError","")
                          }
                      })
          }

      }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}