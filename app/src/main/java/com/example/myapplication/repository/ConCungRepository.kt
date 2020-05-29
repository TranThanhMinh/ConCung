package com.example.myapplication.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.RoomData
import com.example.myapplication.data.Student
import com.example.myapplication.data.UserFB
import com.example.myapplication.data.UserFBDao

class ConCungRepository {

    companion object{
        var db : RoomData?= RoomData.getRoomData()
        var userFB:UserFBDao ?= db!!.userDao()
    }


    fun insertUserFB(userFB: UserFB){
        InsertUserFB().execute(userFB)
    }

    fun deleteUserFB(userFB: UserFB){
        DeleteUserFB().execute(userFB)
    }


    fun getAllUser(): LiveData<List<UserFB>>{
        return  userFB!!.findAll()
    }



    class InsertUserFB : AsyncTask<UserFB, Void, Void>() {
        override fun doInBackground(vararg params: UserFB?): Void? {
            userFB!!.insert(params[0]!!)
            return null
        }

    }
    class DeleteUserFB : AsyncTask<UserFB, Void, Void>() {
        override fun doInBackground(vararg params: UserFB?): Void? {
            userFB!!.delete(params[0]!!)
            return null
        }

    }


}