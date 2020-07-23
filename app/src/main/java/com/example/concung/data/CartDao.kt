package com.example.concung.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {

    @Query("select * from cart")
    fun findAll(): LiveData<List<Cart>>

    @Query("select * from cart where id = :id")
    fun findId(id:String): LiveData<Cart>

    @Insert
    fun insert(cart:Cart)

    @Update
    fun update(cart:Cart)

    @Delete
    fun delete(cart:Cart)
}