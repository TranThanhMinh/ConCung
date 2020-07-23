package com.example.concung.view.product


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.concung.R
import com.example.concung.util.Utility.Companion.addProductFragment
import com.example.concung.util.Utility.Companion.replaceProductFragment
import com.example.concung.view.InterfaceClick
import com.example.concung.view.cart.CartFragment

class InfoProductActivity : AppCompatActivity(),InterfaceClick.OnBack  {
   lateinit var  fm:FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_activity)
        fm = supportFragmentManager
        val type = intent.getSerializableExtra("type")
        if (type == "cart"){
            replaceProductFragment(fm,CartFragment())
        }else addProductFragment(fm,InfoProductFragment())
    }

    override fun onBack() {
        if(fm.backStackEntryCount > 1)
        onBackPressed()
        else finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onBackHome() {
        setResult(100, Intent())
        finish()
    }
}