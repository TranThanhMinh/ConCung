package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.Student
import com.example.myapplication.repository.StudentRepository

class StudentViewModel(application: Application) : AndroidViewModel(application) {
   private var studentRepository:StudentRepository = StudentRepository(application)

    fun insert(student: Student){
        studentRepository.insertStudent(student)
    }
    fun delete(student: Student){
        studentRepository.deleteStudent(student)
    }
    fun update(student: Student){
        studentRepository.updateStudent(student)
    }

    fun getAll():LiveData<List<Student>>{
       return studentRepository.getAllStudent()
    }

    fun getFindIdStudent(id:String):LiveData<List<Student>>{
        return studentRepository.getFindIdStudent(id)
    }
}