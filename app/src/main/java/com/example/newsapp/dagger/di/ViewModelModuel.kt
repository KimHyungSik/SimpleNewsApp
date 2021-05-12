package com.example.newsapp.dagger.di

import androidx.lifecycle.ViewModel
import com.example.newsapp.ViewModel.NewsFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModuel {
    @Binds
    @IntoMap
    @ViewModelKey(NewsFragmentViewModel::class)
    abstract fun bindNewsFragmentViewModel(newsFragmentViewModel: NewsFragmentViewModel): ViewModel
}