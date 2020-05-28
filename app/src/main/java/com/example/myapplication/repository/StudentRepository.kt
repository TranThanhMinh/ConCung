package com.example.myapplication.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.myapplication.data.RoomData
import com.example.myapplication.data.Student
import com.example.myapplication.data.StudentDao

class StudentRepository(var context: Context) {

    companion object{
        var db :RoomData?= RoomData.getRoomData()
        var studentDao:StudentDao?= db!!.studentDao()
    }


    fun insertStudent(student: Student){
        InsertStudent().execute(student)
    }

    fun deleteStudent(student: Student){
        DeleteStudent().execute(student)
    }

    fun updateStudent(student: Student){
        UpdateStudent().execute(student)
    }

    fun getAllStudent(): LiveData<List<Student>>{
        return  studentDao!!.findAll()
    }

    fun getFindIdStudent(id : String): LiveData<List<Student>>{
        return  studentDao!!.findIdStudent(id)
    }

    class InsertStudent : AsyncTask<Student, Void, Void>() {
        override fun doInBackground(vararg params: Student?): Void? {
            studentDao!!.insert(params[0]!!)
            return null
        }

    }
    class DeleteStudent : AsyncTask<Student, Void, Void>() {
        override fun doInBackground(vararg params: Student?): Void? {
            studentDao!!.delete(params[0]!!)
            return null
        }

    }

    class UpdateStudent : AsyncTask<Student, Void, Void>() {
        override fun doInBackground(vararg params: Student?): Void? {
            studentDao!!.update(params[0]!!)
            return null
        }

    }
}