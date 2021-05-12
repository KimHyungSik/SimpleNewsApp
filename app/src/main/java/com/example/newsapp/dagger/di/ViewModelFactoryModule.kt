package com.example.newsapp.dagger.di

import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.ViewModel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory
}