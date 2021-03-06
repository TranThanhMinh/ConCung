package com.example.concung.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.concung.data.RoomData
import com.example.concung.data.UserFB
import com.example.concung.data.UserFBDao

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