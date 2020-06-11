package com.example.myapplication.view


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.model.User
import com.example.myapplication.util.Utility
import com.example.myapplication.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {

    private lateinit var user:User
    private lateinit var loginViewModel:LoginViewModel
    private lateinit var login :Observer<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        login = Observer { boolean ->
            when(boolean) {
                true -> {
                   // Utility.saveUser(this,user)
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                false -> Toast.makeText(this,"Error user or pass",Toast.LENGTH_LONG).show()
            }
        }
        btnLogin.setOnClickListener {
            login()
        }


    }



    override fun onResume() {
        super.onResume()
        getUser()
    }

    fun getUser() {
        val user = Utility.getInfo(this)
        edtUserName.setText(user.id_user)
        editPassWord.setText(user.pass_user)
        login()
    }

    fun login() {
        when {
            //   edtUserName.text.toString().isEmpty() -> Toast.makeText(this,"please enter user",Toast.LENGTH_LONG).show()
            edtUserName.text.toString().isEmpty() -> edtUserName.error = "please enter user"
            //    editPassWord.text.toString().isEmpty() -> Toast.makeText(this,"please enter pass",Toast.LENGTH_LONG).show()
            editPassWord.text.toString().isEmpty() ->  editPassWord.error = "please enter pass"
            else -> {
                user = User(edtUserName.text.toString(), editPassWord.text.toString())
                loginViewModel.loginUser(user).observe(this, login)
            }
        }
    }
}
