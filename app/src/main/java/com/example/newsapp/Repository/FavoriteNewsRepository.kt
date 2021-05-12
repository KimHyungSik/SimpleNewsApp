package com.example.newsapp.Repository

import android.app.Application
import android.content.Context
import com.example.newsapp.Model.FavoriteNewsModel
import com.example.newsapp.Room.AppDatabase
import com.example.newsapp.Room.favoriteList.FavoriteNewsDao

class FavoriteNewsRepository(context: Context){

    private val favoriteNewsDao: FavoriteNewsDao by lazy{
        val db = AppDatabase.getInstance(context)
        db.favoriteNewsModel()
    }

    fun getAllNews(): ArrayList<FavoriteNewsModel>{
        return favoriteNewsDao.getAll() as ArrayList<FavoriteNewsModel>
    }

    fun insert(favoriteNewsModel: FavoriteNewsModel){
        favoriteNewsDao.insertNews(favoriteNewsModel)
    }

    fun delete(favoriteNewsModel: FavoriteNewsModel){
        favoriteNewsDao.delete(favoriteNewsModel)
    }

    fun searchTitle(query: String): ArrayList<FavoriteNewsModel>{
        return favoriteNewsDao.searchNewsTitle("%$query%") as ArrayList<FavoriteNewsModel>
    }
}