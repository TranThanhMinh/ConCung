package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserFBDao {
    @Query("select * from user_tb")
    fun findAll(): LiveData<List<UserFB>>

    @Insert
    fun insert(student:UserFB)

    @Update
    fun update(student:UserFB)

    @Delete
    fun delete(student:UserFB)
}