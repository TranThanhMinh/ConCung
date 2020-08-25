package com.example.concung.view.adapter

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.concung.R
import com.example.concung.model.shop.AddressShop
import com.google.android.gms.maps.model.LatLng
import java.io.IOException


class ShopsAdapter(var context: Context) : RecyclerView.Adapter<ShopsAdapter.MyViewHolder>() {
    var listShop: List<AddressShop>? = ArrayList()
    var current: LatLng? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shop, parent, false))
    }

    fun loadData(listShop: List<AddressShop>, current: LatLng?) {
        this.listShop = listShop
        this.current = current
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listShop!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listShop!![position]
        holder.tvShop.text = item.getAddress()

        holder.btnDirect.setOnClickListener {
            //view maps
            var location = getLocationFromAddress(item.getAddress())
            val uri = "http://maps.google.com/maps?saddr=" + current!!.latitude.toString() + "," + current!!.longitude.toString() + "(" + "Home Sweet Home" + ")&daddr=" + location!!.latitude.toString() + "," + location!!.longitude.toString() + " (" + "Where the party is at" + ")"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            context.startActivity(intent)
        }
    }

    private fun getLocationFromAddress(strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            p1 = LatLng(location.latitude, location.longitude)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return p1
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvShop = view.findViewById<TextView>(R.id.tvShop)
        var btnDirect = view.findViewById<TextView>(R.id.btnDirect)
    }
}