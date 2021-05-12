package com.example.newsapp.Repository

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

class DataRepository(context: Context) {

    val queryHistoryRepository = QueryHistoryRepository(context)
    val favoriteNewsRepository = FavoriteNewsRepository(context)

    companion object{
        private var instance: DataRepository? = null
        public fun getInstance(context: Context):DataRepository{
            return instance ?:synchronized(this) {
                instance ?: DataRepository(context).also {
                    instance = it
                }
            }
        }
    }

}