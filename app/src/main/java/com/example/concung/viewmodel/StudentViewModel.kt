package com.example.concung.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.concung.data.Student
import com.example.concung.repository.StudentRepository

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