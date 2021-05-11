package com.example.newsapp.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.Repository.DataRepository

class ViewModelFactory(val appliction: Application): ViewModelProvider.Factory {

    companion object{
        private var instance: ViewModelFactory? = null
        fun getInstance(appliction: Application) = instance ?: synchronized(ViewModelFactory::class.java){
            instance ?: ViewModelFactory(appliction).also { instance = it }
        }
    }

    val dataRepository: DataRepository by lazy{
        DataRepository.getInstance(appliction)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass){
            when {
                isAssignableFrom(NewsFragmentViewModel::class.java) -> NewsFragmentViewModel.getInstance(dataRepository)
                else -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
            }
        }
     as T

}