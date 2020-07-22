package com.example.myapplication.view

import com.example.myapplication.data.Cart
import com.example.myapplication.data.ProductWatched
import com.example.myapplication.data.Student
import com.example.myapplication.model.Category
import com.example.myapplication.model.product.Product
import com.example.myapplication.model.user.Address

interface InterfaceClick {
    interface Adapter{
        fun clickItem(student: Student)
        fun clickChooseDelete(check: Boolean)
    }

    interface  EventProduct{
         fun detailProduct(id:Product)
         fun detailProductWatch(id:ProductWatched)
    }

    interface EventCategory{
        fun gotoCategory(category: Category)
    }

    interface EventCart{
        fun deleteProduct(cart: Cart)
    }

    interface EventAddress{
        fun showUpdate(address: Address)
        fun showAddressDefault(id2:Int,type2:Int)
    }

    interface home{
        fun openMenu()
        fun closeMenu()
    }

    interface OnBack {
        fun onBack()
        fun onBackHome()
    }

}