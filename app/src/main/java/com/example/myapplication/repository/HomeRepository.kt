package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.dagger.Component.DaggerRetrofitComponent
import com.example.myapplication.dagger.Component.RetrofitComponent
import com.example.myapplication.model.RequestId
import com.example.myapplication.model.ResultApi
import com.example.myapplication.model.comment.ResquetComment
import com.example.myapplication.model.comment.ResultComment
import com.example.myapplication.model.comment.ResultStatus
import com.example.myapplication.model.news.ResultNews
import com.example.myapplication.model.product.ResultIdProduct
import com.example.myapplication.model.product.ResultProduct
import com.example.myapplication.model.product.ResultUpload
import com.example.myapplication.model.trademark.ResultTrademark
import com.example.myapplication.retrofit.Api
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class HomeRepository {

    @Inject
    lateinit var api: Api

    init {
        val retrofitComponent: RetrofitComponent = DaggerRetrofitComponent.builder().build()
        retrofitComponent.inject(this)
    }

    fun getCategory(): LiveData<ResultApi> {
        val list: MutableLiveData<ResultApi> = MutableLiveData()
        val call: Call<ResultApi> = api.getCategory()
        call.enqueue(object : Callback<ResultApi> {
            override fun onFailure(call: Call<ResultApi>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultApi>, response: Response<ResultApi>) {
                list.value = response.body()
            }

        })
        return list
    }

    fun getProduct(): LiveData<ResultProduct> {
        val list: MutableLiveData<ResultProduct> = MutableLiveData()
        val call: Call<ResultProduct> = api.getProduct()
        call.enqueue(object : Callback<ResultProduct> {
            override fun onFailure(call: Call<ResultProduct>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultProduct>, response: Response<ResultProduct>) {
                list.value = response.body()
            }

        })
        return list
    }

    fun getTrademark(): LiveData<ResultTrademark> {
        val list: MutableLiveData<ResultTrademark> = MutableLiveData()
        val call: Call<ResultTrademark> = api.getTrademark()
        call.enqueue(object : Callback<ResultTrademark> {
            override fun onFailure(call: Call<ResultTrademark>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultTrademark>, response: Response<ResultTrademark>) {
                list.value = response.body()
            }

        })
        return list
    }


    fun getIdProduct(id: RequestId): LiveData<ResultIdProduct> {
        val list: MutableLiveData<ResultIdProduct> = MutableLiveData()
        val call: Call<ResultIdProduct> = api.getIdProduct(id)
        call.enqueue(object : Callback<ResultIdProduct> {
            override fun onFailure(call: Call<ResultIdProduct>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultIdProduct>, response: Response<ResultIdProduct>) {
                list.value = response.body()
            }

        })
        return list
    }


    fun getNews(): LiveData<ResultNews> {
        val list: MutableLiveData<ResultNews> = MutableLiveData()
        var call: Call<ResultNews> = api.getNews()
        call.enqueue(object : Callback<ResultNews> {
            override fun onFailure(call: Call<ResultNews>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultNews>, response: Response<ResultNews>) {
                list.value = response.body()
            }

        })
        return list
    }

    fun getInsertComment(comment: ResquetComment): LiveData<ResultStatus> {
        val list: MutableLiveData<ResultStatus> = MutableLiveData()
        var call: Call<ResultStatus> = api.getInsertComment(comment)
        call.enqueue(object : Callback<ResultStatus> {
            override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultStatus>, response: Response<ResultStatus>) {
                list.value = response.body()
            }

        })
        return list
    }


    fun getComment(id: RequestId): LiveData<ResultComment> {
        val list: MutableLiveData<ResultComment> = MutableLiveData()
        var call: Call<ResultComment> = api.getComment(id)
        call.enqueue(object : Callback<ResultComment> {
            override fun onFailure(call: Call<ResultComment>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultComment>, response: Response<ResultComment>) {
                //    LogUtils.api("HomeRepository",call,response)
                list.value = response.body()
            }

        })
        return list
    }

    fun imageUpLoad(filePath: String,idComment:Int): LiveData<ResultUpload> {
        val list: MutableLiveData<ResultUpload> = MutableLiveData()
        //Create a file object using file path
        var file = File(filePath);
        // Create a request body with file and image media type
        var fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        var part = MultipartBody.Part.createFormData("photo", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        var description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        val idComment: RequestBody = RequestBody.create(MediaType.parse("text/plain"), idComment.toString())
        //
        var call = api.imageUpload(part, description,idComment);
        call.enqueue(object : Callback<ResultUpload> {
            override fun onFailure(call: Call<ResultUpload>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultUpload>, response: Response<ResultUpload>) {
                list.value = response.body()
            }

        })
        return list
    }
}