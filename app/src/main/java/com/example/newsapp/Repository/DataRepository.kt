package com.example.newsapp.Repository

import android.app.Application

class DataRepository(application: Application) {

    val queryHistoryRepository = QueryHistoryRepository(application)

    companion object{
        private var instance: DataRepository? = null
        fun getInstance(application: Application):DataRepository{
            return instance ?:synchronized(this) {
                instance ?: DataRepository(application).also {
                    instance = it
                }
            }
        }
    }

}