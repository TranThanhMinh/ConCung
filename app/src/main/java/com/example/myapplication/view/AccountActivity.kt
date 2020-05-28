package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.account_activity.*


class AccountActivity:AppCompatActivity(),View.OnClickListener {
    var callbackManager: CallbackManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)

        init()
    }

    fun init(){
        FacebookSdk.sdkInitialize(this)
        imCancel.setOnClickListener(this)
        callbackManager = CallbackManager.Factory.create();
        login_button.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        // App code

                        Log.e("Minh","onSuccess")
                    }

                    override fun onCancel() {
                        Log.e("Minh","onCancel")
                        // App code
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                        Log.e("Minh","onError")
                    }
                })

    }

    override fun onClick(v: View?) {
      when(v){
          imCancel->{
              finish()
          }

      }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}