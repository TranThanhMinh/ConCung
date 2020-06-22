package com.example.myapplication.view

import com.example.myapplication.data.Cart
import com.example.myapplication.data.Student

interface InterfaceClick {
    interface Adapter{
        fun clickItem(student: Student)
        fun clickChooseDelete(check: Boolean)
    }

    interface  Product{
         fun detailProduct(id:String)
    }

    interface EventCart{
        fun deleteProduct(cart: Cart)
    }

    interface home{
        fun openMenu()
    }

}