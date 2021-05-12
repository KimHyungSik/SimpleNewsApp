package com.example.newsapp.Repository

import android.app.Application
import dagger.Module
import dagger.Provides

class DataRepository(application: Application) {

    val queryHistoryRepository = QueryHistoryRepository(application)
    val favoriteNewsRepository = FavoriteNewsRepository(application)

    companion object{
        private var instance: DataRepository? = null
        public fun getInstance(application: Application):DataRepository{
            return instance ?:synchronized(this) {
                instance ?: DataRepository(application).also {
                    instance = it
                }
            }
        }
    }

}