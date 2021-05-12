package com.example.newsapp.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.Repository.DataRepository
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    val viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModelMap[modelClass]?.get() as T
    }
}