package com.example.newsapp.dagger.di

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.newsapp.Repository.DataRepository
import com.example.newsapp.Utils.Utility.TAG
import dagger.Module
import dagger.Provides

@Module
class DataRepositryModule {

    @Provides
    fun provideDataRepositry(context: Context): DataRepository{
        Log.d(TAG, "provideDataRepositry: Module")
        return DataRepository.getInstance(context)
    }
}