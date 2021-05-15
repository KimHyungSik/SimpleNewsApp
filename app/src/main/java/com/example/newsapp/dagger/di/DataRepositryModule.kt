package com.example.newsapp.dagger.di

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.newsapp.Repository.DataRepository
import com.example.newsapp.Repository.FavoriteNewsRepository
import com.example.newsapp.Repository.QueryHistoryRepository
import com.example.newsapp.Utils.Utility.TAG
import dagger.Module
import dagger.Provides

@Module
class DataRepositryModule {

    @Provides
    fun provideDataRepositry(queryHistoryRepository: QueryHistoryRepository, favoriteNewsRepository: FavoriteNewsRepository): DataRepository{
        Log.d(TAG, "provideDataRepositry: Module")
        return DataRepository.getInstance(queryHistoryRepository, favoriteNewsRepository)
    }

    @Provides
    fun provideQueryHistoryRepositroy(context: Context): QueryHistoryRepository{
        return QueryHistoryRepository(context)
    }

    @Provides
    fun provideFavoriteNewsHistoryRepositroy(context: Context): FavoriteNewsRepository{
        return FavoriteNewsRepository(context)
    }
}