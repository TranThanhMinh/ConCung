package com.example.myapplication.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.UserFB
import com.example.myapplication.model.User
import com.example.myapplication.util.Utility
import com.example.myapplication.viewmodel.ConCungViewModel
import com.example.myapplication.viewmodel.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.account_activity.*


class LoginActivity:AppCompatActivity(),View.OnClickListener {
    var callbackManager: CallbackManager?=null
    var concung: ConCungViewModel?=null
    private var loginViewModel: LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)
        concung = ViewModelProviders.of(this).get(ConCungViewModel::class.java)
        init()
    }

    fun init(){
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
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
        login_fb.setOnClickListener(this)
        btnLogin.setOnClickListener(this)


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
          login_fb->{//login facebook
              LoginManager.getInstance().registerCallback(callbackManager,
                      object : FacebookCallback<LoginResult?> {
                          override fun onSuccess(loginResult: LoginResult?) {
                              // App code
                              val  imageURL = "https://graph.facebook.com/"+loginResult!!.accessToken.userId+"/picture?type=normal";
                              saveData(loginResult!!.accessToken.userId,"Facebook",imageURL)

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
          btnLogin->{//login phone nummber
              var user = User(editPhone.text.toString(),editPassWord.text.toString())
              loginViewModel!!.login(user).observe(this, Observer { item->
                  if (item != null){
                      saveData(item.getUser()[0].getIdUser()!!,item.getUser()[0].getNameUser()!!,item.getUser()[0].getImage()!!)
                  }
              })
          }

      }
    }

    fun saveData(id:String,name_user:String, image:String){
        val userFB = UserFB()
        userFB.id = id
        userFB.name_user = name_user
        userFB.image = image
        userFB.image_user = image
        concung!!.insert(userFB)

        val intent = Intent()
        intent.putExtra("id",id)
        intent.putExtra("image",image)
        intent.putExtra("name_user",name_user)
        intent.putExtra("image",image)
        setResult(2,intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}