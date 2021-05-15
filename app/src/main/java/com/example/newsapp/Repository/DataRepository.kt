package com.example.newsapp.Repository

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class DataRepository (val queryHistoryRepository: QueryHistoryRepository, val favoriteNewsRepository: FavoriteNewsRepository ) {

    companion object{
        private var instance: DataRepository? = null
        public fun getInstance(queryHistoryRepository: QueryHistoryRepository, favoriteNewsRepository: FavoriteNewsRepository):DataRepository{
            return instance ?:synchronized(this) {
                instance ?: DataRepository(queryHistoryRepository, favoriteNewsRepository).also {
                    instance = it
                }
            }
        }
    }

}