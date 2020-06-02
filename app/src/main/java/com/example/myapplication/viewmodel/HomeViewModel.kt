package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.RequestId
import com.example.myapplication.model.ResultApi
import com.example.myapplication.model.product.ResultIdProduct
import com.example.myapplication.model.product.ResultProduct
import com.example.myapplication.model.trademark.ResultTrademark
import com.example.myapplication.repository.HomeRepository
import java.lang.reflect.Array

class HomeViewModel : ViewModel() {

    private var homeRopository: HomeRepository?=null
    init {
        homeRopository = HomeRepository()
    }
    fun getData():LiveData<ResultApi>{
        return homeRopository!!.getCategory()
    }

    fun getProduct():LiveData<ResultProduct>{
        return homeRopository!!.getProduct()
    }

    fun getIdProduct(id: RequestId):LiveData<ResultIdProduct>{
        return homeRopository!!.getIdProduct(id)
    }

    fun getTrademark():LiveData<ResultTrademark>{
        return homeRopository!!.getTrademark()
    }
}