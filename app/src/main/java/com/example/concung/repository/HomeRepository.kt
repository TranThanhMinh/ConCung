package com.example.concung.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.concung.dagger.Component.DaggerRetrofitComponent
import com.example.concung.dagger.Component.RetrofitComponent
import com.example.concung.data.*
import com.example.concung.model.home.RequestId
import com.example.concung.model.home.ResultApi
import com.example.concung.model.category.ResultCategory
import com.example.concung.model.home.User
import com.example.concung.model.cart.OrderAddress
import com.example.concung.model.cart.ResultGetOrder
import com.example.concung.model.cart.ResultNewOrder
import com.example.concung.model.comment.ResquetComment
import com.example.concung.model.comment.ResultComment
import com.example.concung.model.comment.ResultStatus
import com.example.concung.model.news.ResultNews
import com.example.concung.model.product.ResultIdProduct
import com.example.concung.model.product.ResultProduct
import com.example.concung.model.product.ResultUpload
import com.example.concung.model.trademark.ResultTrademark
import com.example.concung.model.user.Address
import com.example.concung.model.user.ResultAddress
import com.example.concung.retrofit.Api
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class HomeRepository {
    companion object{
        var db : RoomData?= RoomData.getRoomData()
        var cartDao: CartDao?= db!!.cartDao()
        var productwatchedDao: ProductWatchedDao?= db!!.productwatchedDao()
    }

    @Inject
    lateinit var api: Api

    init {
        val retrofitComponent: RetrofitComponent = DaggerRetrofitComponent.builder().build()
        retrofitComponent.inject(this)
    }



    fun getCategory(): LiveData<ResultCategory> {
        val list: MutableLiveData<ResultCategory> = MutableLiveData()
        val call: Call<ResultCategory> = api.getCategory()
        call.enqueue(object : Callback<ResultCategory> {
            override fun onFailure(call: Call<ResultCategory>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultCategory>, response: Response<ResultCategory>) {
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

    fun getCategory(id: RequestId): LiveData<ResultCategory> {
        val list: MutableLiveData<ResultCategory> = MutableLiveData()
        val call: Call<ResultCategory> = api.getTypeCategory(id)
        call.enqueue(object : Callback<ResultCategory> {
            override fun onFailure(call: Call<ResultCategory>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultCategory>, response: Response<ResultCategory>) {
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
                //LogUtils.api("",call,"response")
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
                list.value = response.body()
            }

        })
        return list
    }


    fun getAddress(id: User): LiveData<ResultAddress> {
        val list: MutableLiveData<ResultAddress> = MutableLiveData()
        var call: Call<ResultAddress> = api.getAddress(id)
        call.enqueue(object : Callback<ResultAddress> {
            override fun onFailure(call: Call<ResultAddress>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultAddress>, response: Response<ResultAddress>) {
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
        var description = RequestBody.create(MediaType.parse("text/plain"), "image-type")
        val idComment: RequestBody = RequestBody.create(MediaType.parse("text/plain"), idComment.toString())
        val nameImage: RequestBody = RequestBody.create(MediaType.parse("text/plain"), System.currentTimeMillis().toString())
        //
        var call = api.imageUpload(part, description,idComment,nameImage)
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

    fun insertCart(cart: Cart){
        InsertCart().execute(cart)
    }

    fun getCart():LiveData<List<Cart>>{
       return cartDao!!.findAll()
    }

    fun getIdCart(id:String):LiveData<Cart>{
        return cartDao!!.findId(id)
    }

    fun updateCart(cart: Cart){
        UpdateCart().execute(cart)
    }

    fun deleteCart(cart: Cart){
        DeleteCart().execute(cart)
    }

    fun productExist(id: String):LiveData<List<ProductWatched>>{
        return  productwatchedDao!!.findId(id)
    }

    fun insertProduct(product: ProductWatched){
         InsertProduct().execute(product)
    }

    fun updateProduct(product: ProductWatched){
        UpdateProduct().execute(product)
    }

    fun getProductWatched():LiveData<List<ProductWatched>>{
        return productwatchedDao!!.findAll()
    }

    fun getProductLove():LiveData<List<ProductWatched>>{
        return productwatchedDao!!.findProductLove()
    }

    fun newOrder(address: OrderAddress):LiveData<ResultNewOrder>{
          val list:MutableLiveData<ResultNewOrder> = MutableLiveData()
          val call:Call<ResultNewOrder> =api.newOrder(address)
              call.enqueue(object : Callback<ResultNewOrder>{
                  override fun onFailure(call: Call<ResultNewOrder>, t: Throwable) {
                      list.value = null
                  }

                  override fun onResponse(call: Call<ResultNewOrder>, response: Response<ResultNewOrder>) {
                      list.value = response.body()
                  }

              })
        return list
    }

    fun getOrder(user: User):LiveData<ResultGetOrder>{
        val list:MutableLiveData<ResultGetOrder> = MutableLiveData()
        val call:Call<ResultGetOrder> =api.getOrder(user)
        call.enqueue(object : Callback<ResultGetOrder>{
            override fun onFailure(call: Call<ResultGetOrder>, t: Throwable) {
                list.value = null
            }

            override fun onResponse(call: Call<ResultGetOrder>, response: Response<ResultGetOrder>) {
                list.value = response.body()
            }

        })
        return list
    }
    /**
     * function update Or Insert of Address
     */
    fun updateOrInsert(address: Address):LiveData<ResultApi>{
        val list: MutableLiveData<ResultApi> = MutableLiveData()
        var call: Call<ResultApi> = api.updateOrInsert(address)
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

    class InsertCart: AsyncTask<Cart, Void, Void>() {
        override fun doInBackground(vararg params: Cart): Void? {
            cartDao!!.insert(params[0]!!)
            return null
        }
    }



    class UpdateCart: AsyncTask<Cart, Void, Void>() {
        override fun doInBackground(vararg params: Cart): Void? {
            cartDao!!.update(params[0]!!)
            return null
        }
    }

    class DeleteCart: AsyncTask<Cart, Void, Void>() {
        override fun doInBackground(vararg params: Cart): Void? {
            cartDao!!.delete(params[0]!!)
            return null
        }
    }

    class InsertProduct: AsyncTask<ProductWatched, Void, Void>() {
        override fun doInBackground(vararg params: ProductWatched): Void? {
            productwatchedDao!!.insert(params[0]!!)
            return null
        }
    }

    class UpdateProduct: AsyncTask<ProductWatched, Void, Void>() {
        override fun doInBackground(vararg params: ProductWatched): Void? {
            productwatchedDao!!.update(params[0]!!)
            return null
        }
    }
}