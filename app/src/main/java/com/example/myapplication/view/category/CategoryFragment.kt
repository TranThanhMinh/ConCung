package com.example.myapplication.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.util.Utility
import com.example.myapplication.view.adapter.ImageAdapter
import kotlinx.android.synthetic.main.item_home_2.*

class CategoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    fun init(){
        var list = ArrayList<String>()
        for (i in 0..10) {
            list.add("https://9mobi.vn/cf/images/2015/03/nkk/hinh-dep-1.jpg")
        }
        val adapter = ImageAdapter(context!!,list!!)
        viewPager.adapter = adapter
        Utility.addBottomDots(layout_dots,list!!.size,0,context!!)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                Utility.addBottomDots(layout_dots,list!!.size,position,context!!)
            }

        })
    }
}