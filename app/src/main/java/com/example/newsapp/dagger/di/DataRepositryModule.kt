package com.example.newsapp.dagger.di

import android.app.Application
import com.example.newsapp.Repository.DataRepository
import dagger.Module
import dagger.Provides

@Module
class DataRepositryModule {

    @Provides
    fun provideDataRepositry(application: Application): DataRepository{
        return DataRepository.getInstance(application)
    }
}