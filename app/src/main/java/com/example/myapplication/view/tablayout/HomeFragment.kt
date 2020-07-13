package com.example.myapplication.view.tablayout


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.util.Utility.Companion.replaceHomeFragment
import com.example.myapplication.view.InterfaceClick
import com.example.myapplication.view.product.InfoProductActivity
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {
    lateinit var fm: FragmentManager
    private var click: InterfaceClick.home? = null

    fun initClick(click: InterfaceClick.home) {
        this.click = click
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        fm = activity!!.supportFragmentManager

        imMenu.setOnClickListener {
            click!!.openMenu()
            imMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back))
        }

        cart.setOnClickListener {
            val intent = Intent(context, InfoProductActivity::class.java)
            intent.putExtra("type","cart")
            startActivity(intent)
        }

        replaceHomeFragment(fm, HomeFragment2())

    }


}