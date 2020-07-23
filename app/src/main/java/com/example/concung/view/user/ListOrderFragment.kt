package com.example.concung.view.user

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.concung.R
import com.example.concung.model.home.User
import com.example.concung.model.cart.Order
import com.example.concung.model.user.Address
import com.example.concung.util.Utility
import com.example.concung.view.InterfaceClick
import com.example.concung.view.adapter.ListOrderAdapter
import com.example.concung.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.list_order_fragment.*
import java.io.Serializable

class ListOrderFragment : Fragment(), ListOrderAdapter.ListOrder  {
    private var homeViewModel: HomeViewModel? = null
    private var adapter: ListOrderAdapter? = null
    lateinit var back: InterfaceClick.OnBack
    private var id2:Int?=null
    private var type2:Int?=null
    lateinit var fmUser: FragmentManager
    lateinit var list:List<Order>
    lateinit var listSort:List<Order>


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        back = context as InterfaceClick.OnBack
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.list_order_fragment, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(toolbar)
        activity!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity!!.supportActionBar!!.title = resources.getString(R.string.txt_manager_order)

        setHasOptionsMenu(true)

        homeViewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
        fmUser = activity!!.supportFragmentManager

        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rvOrder.layoutManager = layout
        adapter = ListOrderAdapter(context!!,this)
        rvOrder.adapter = adapter

        getOrder()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        return inflater!!.inflate(R.menu.menu_sort_order,menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                back.onBack()
            }
            R.id.item_canceled->{
                sortOrder(0)
            }
            R.id.item_ok->{
                sortOrder(1)
            }
            R.id.item_finished->{
                sortOrder(2)
            }
            R.id.item_all->{
                adapter!!.loadData(list as ArrayList<Order>)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * function sort list order
     * @param type: type of order
     */
    private fun sortOrder(type:Int){
        listSort = ArrayList()
        for (i in list) {
            if (i.getStatus() == type) {
                (listSort as ArrayList).add(i)
            }
        }
        adapter!!.loadData(listSort as ArrayList<Order>)
    }


    fun getOrder() {
        val user = User(Utility.id_user, null)
        homeViewModel!!.getOrder(user).observe(this, Observer { list ->
            progress_bar.visibility = View.GONE
            if (list != null) {
                this.list = list.getData()
                adapter!!.loadData(this.list as ArrayList<Order>)
            }
        })
    }


    override fun viewList(order: Order) {
        val detail = DetailOrderFragment()
        val bundle = Bundle()
        bundle.putSerializable("order",order as Serializable)
        detail.arguments = bundle
        Utility.replaceFragment(fmUser,detail)
    }

}