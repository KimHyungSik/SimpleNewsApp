package com.example.newsapp.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.Repository.DataRepository

class ViewModelFactory(val appliction: Application): ViewModelProvider.Factory {
    val dataRepository: DataRepository by lazy{
        DataRepository.getInstance(appliction)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass == NewsFragmentViewModel::class.java){
            return NewsFragmentViewModel(dataRepository) as T
        }else { throw IllegalArgumentException("unknown model class") }
    }
}