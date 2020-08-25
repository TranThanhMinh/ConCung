package com.example.concung.view.login

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.concung.R
import com.example.concung.data.UserFB
import com.example.concung.data.sharedpreference.TypeLogin.Companion.saveLogin
import com.example.concung.model.home.User
import com.example.concung.viewmodel.ConCungViewModel
import com.example.concung.viewmodel.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.account_activity.*
import java.security.MessageDigest


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var callbackManager: CallbackManager? = null
    var concung: ConCungViewModel? = null
    private var loginViewModel: LoginViewModel? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var RC_SIGN_IN = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)
        concung = ViewModelProviders.of(this).get(ConCungViewModel::class.java)
        init()
    }

    fun init() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        try {
            val packageInfo = packageManager.getPackageInfo("com.example.myapplication", PackageManager.GET_SIGNATURES)
            for (signature in packageInfo.signatures) {
                val messageDigest: MessageDigest = MessageDigest.getInstance("SHA")
                messageDigest.update(signature.toByteArray())
                Log.e("KeyHash", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT))
            }
        } catch (e: Exception) {
        }
        FacebookSdk.sdkInitialize(this)
        imCancel.setOnClickListener(this)
        login_fb.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        login_mail.setOnClickListener(this)


        callbackManager = CallbackManager.Factory.create()

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    override fun onStart() {
        super.onStart()
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
    }

    fun showToast(text: String, url: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        when (v) {
            imCancel -> {
                setResult(3, Intent())
                finish()
            }
            login_fb -> {//login facebook
                LoginManager.getInstance().registerCallback(callbackManager,
                        object : FacebookCallback<LoginResult?> {
                            override fun onSuccess(loginResult: LoginResult?) {
                                // App code
                                val imageURL = "https://graph.facebook.com/" + loginResult!!.accessToken.userId + "/picture?type=normal"
                                saveData(loginResult!!.accessToken.userId, "Facebook", imageURL,1)

                            }

                            override fun onCancel() {
                                showToast("onCancel", "")
                                // App code
                            }

                            override fun onError(exception: FacebookException) {
                                // App code
                                showToast("onError", "")
                            }
                        })
            }
            btnLogin -> {//login phone nummber
                var user = User(editPhone.text.toString(), editPassWord.text.toString())
                loginViewModel!!.login(user).observe(this, Observer { item ->
                    if (item != null) {
                        saveData(item.getUser()[0].getIdUser()!!, item.getUser()[0].getNameUser()!!, item.getUser()[0].getImage()!!,0)
                    }
                })
            }
            login_mail -> {
                signIn()
            }

        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun saveData(id: String, name_user: String, image: String,type:Int) {
        //save type of login
        saveLogin(this,type)

        val userFB = UserFB()
        userFB.id = id
        userFB.name_user = name_user
        userFB.image = image
        userFB.image_user = image
        concung!!.insert(userFB)

        val intent = Intent()
        intent.putExtra("id", id)
        intent.putExtra("image", image)
        intent.putExtra("name_user", name_user)
        intent.putExtra("image", image)
        setResult(2, intent)
        finish()
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
           // updateUI(account)

            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                val personPhoto: Uri? = acct.photoUrl

                saveData(personId!!, "$personFamilyName $personGivenName", "minh",2)
            }
            finish()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode())
         //   updateUI(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode === RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
}