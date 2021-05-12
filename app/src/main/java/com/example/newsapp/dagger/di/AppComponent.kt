package com.example.newsapp.dagger.di

import android.content.Context
import com.example.newsapp.Fragments.NewsFragment
import com.example.newsapp.MainActivity
import com.example.newsapp.Repository.DataRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    DataRepositryModule::class
])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: NewsFragment)
}