package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductWatchedDao {

    @Insert
    fun insert(product: ProductWatched)

    @Update
    fun update(product: ProductWatched)

    @Query("select * from productwatched")
    fun findAll():LiveData<List<ProductWatched>>

    @Query("select * from productwatched where id =:id")
    fun findId(id:String):LiveData<List<ProductWatched>>

    @Query("select * from productwatched where love = 1")
    fun findProductLove():LiveData<List<ProductWatched>>
}