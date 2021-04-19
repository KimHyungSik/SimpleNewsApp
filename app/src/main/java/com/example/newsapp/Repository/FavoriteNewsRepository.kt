package com.example.newsapp.Repository

import android.app.Application
import com.example.newsapp.Model.FavoriteNewsModel
import com.example.newsapp.Room.AppDatabase
import com.example.newsapp.Room.favoriteList.FavoriteNewsDao

class FavoriteNewsRepository(application: Application){

    private val favoriteNewsDao: FavoriteNewsDao by lazy{
        val db = AppDatabase.getInstance(application)
        db.favoriteNewsModel()
    }

    fun getAllNews(): List<FavoriteNewsModel>{
        return favoriteNewsDao.getAll()
    }

    fun insert(favoriteNewsModel: FavoriteNewsModel){
        favoriteNewsDao.insertNews(favoriteNewsModel)
    }

    fun delete(favoriteNewsModel: FavoriteNewsModel){
        favoriteNewsDao.delete(favoriteNewsModel)
    }

    fun searchTitle(query: String): List<FavoriteNewsModel>{
        return favoriteNewsDao.searchNewsTitle(query)
    }
}