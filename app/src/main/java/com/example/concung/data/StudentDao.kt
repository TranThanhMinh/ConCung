package com.example.concung.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao {
    @Query("select * from student")
    fun findAll(): LiveData<List<Student>>

    @Query("select * from student where idstudent = :id")
    fun findIdStudent(id:String): LiveData<List<Student>>

    @Insert
    fun insert(student:Student)

    @Update
    fun update(student:Student)

    @Delete
    fun delete(student:Student)
}