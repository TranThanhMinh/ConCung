package com.example.concung.view.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.example.concung.R
import com.example.concung.util.Utility
import com.example.concung.util.Utility.Companion.replaceFragment
import com.example.concung.view.user.AddressFragment
import com.example.concung.view.user.ListOrderFragment
import com.example.concung.view.user.ProductLoveFragment
import com.example.concung.view.user.ProductWatchedFragment
import com.example.concung.viewmodel.ConCungViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_fragment.*
import kotlinx.android.synthetic.main.user_fragment.imUser
import kotlinx.android.synthetic.main.user_fragment.tvName1
import kotlinx.android.synthetic.main.user_fragment.tvName2

class UserFragment : Fragment(),View.OnClickListener  {


    lateinit var fmUser: FragmentManager
    private var concung: ConCungViewModel?=null
    var mHome: goHome?=null


    interface goHome{
        fun logOut()
    }

    fun init(mHome: goHome){
        this.mHome =mHome
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.user_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        concung = ViewModelProviders.of(this).get(ConCungViewModel::class.java)
        fmUser = activity!!.supportFragmentManager
        setInformationUser()
        btnLogOut.setOnClickListener(this)
        llAddress.setOnClickListener(this)
        llProductWatched.setOnClickListener(this)
        llProductLove.setOnClickListener(this)
        llOrder.setOnClickListener(this)

    }

    private fun setInformationUser(){
        if(Utility.name_user != null) {
            Picasso.with(context).load(Utility.image_user).error(R.drawable.ic_user).into(imUser)
            tvName1.text = Utility.name_user
            tvName2.text = resources.getString(R.string.txt_manager)
        }else {
            Picasso.with(context).load(R.drawable.ic_user).into(imUser)
            tvName1.text = resources.getString(R.string.txt_login)
            tvName2.text = resources.getString(R.string.txt_logout)
        }
    }

    override fun onClick(v: View?) {
       when(v){
           llAddress->{
               replaceFragment(fmUser,AddressFragment())
           }
           llProductWatched->{
               replaceFragment(fmUser,ProductWatchedFragment())
           }

           llProductLove->{
               replaceFragment(fmUser,ProductLoveFragment())
           }

           btnLogOut->{
               mHome!!.logOut()
           }
           llOrder->{
               replaceFragment(fmUser,ListOrderFragment())
           }
       }
    }

  /*  override fun onBack() {
        var count = fmUser.backStackEntryCount
        while(count > 1){
            fmUser.popBackStackImmediate("UserFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            count-=1
        }
    }*/
}