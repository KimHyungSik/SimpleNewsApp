package com.example.newsapp

import android.app.Application
import com.example.newsapp.dagger.di.AppComponent
import com.example.newsapp.dagger.di.ApplicationModule
import com.example.newsapp.dagger.di.DaggerAppComponent
import com.example.newsapp.dagger.di.DataRepositryModule

class MyApplication: Application() {

    lateinit var appComponent: AppComponent

    companion object{
        lateinit var instance : MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule((this)))
            .dataRepositryModule(DataRepositryModule())
            .build()
        instance = this
    }

}