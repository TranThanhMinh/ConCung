package com.example.concung.view

import com.example.concung.data.Cart
import com.example.concung.data.ProductWatched
import com.example.concung.data.Student
import com.example.concung.model.category.Category
import com.example.concung.model.product.Product
import com.example.concung.model.user.Address

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