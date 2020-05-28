package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Example
import com.example.myapplication.repository.ExampleRepository


class ExampleViewModel : ViewModel() {
    private lateinit var exampleRepository: ExampleRepository
    fun getExample (): LiveData<Example>{
        exampleRepository = ExampleRepository()
       return exampleRepository.getExample()
    }
}