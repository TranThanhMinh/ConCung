package com.example.concung.view.tablayout


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.concung.R
import com.example.concung.util.Utility.Companion.replaceHomeFragment
import com.example.concung.view.InterfaceClick
import com.example.concung.view.product.InfoProductActivity
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment(),HomeFragment.IconMenu {
    lateinit var fm: FragmentManager
    private var click: InterfaceClick.home? = null
    var page = 0
    lateinit var back: InterfaceClick.OnBack

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        back = context as InterfaceClick.OnBack
    }

    fun initClick(click: InterfaceClick.home) {
        this.click = click
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        fm = activity!!.supportFragmentManager
        imMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_menu))
        imMenu.setOnClickListener {
            if (page > 0){
                page-=1
                back.onBack()
                imMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_menu))
            }else click!!.openMenu()
        }

        cart.setOnClickListener {
            val intent = Intent(context, InfoProductActivity::class.java)
            intent.putExtra("type","cart")
            startActivity(intent)
        }
        val homeFragment = HomeFragment()
        homeFragment.onAttach(this)
        replaceHomeFragment(fm, homeFragment)

    }

    override fun setIcon() {
        page+= 1
        imMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back))
    }


}