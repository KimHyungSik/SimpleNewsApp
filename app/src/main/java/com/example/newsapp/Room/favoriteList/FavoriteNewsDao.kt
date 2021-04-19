package com.example.newsapp.Room.favoriteList

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.Model.FavoriteNewsModel

@Dao
interface FavoriteNewsDao {

    @Query("SELECT * FROM FavoriteNews")
    fun getAll(): ArrayList<FavoriteNewsModel>

    @Query("SELECT * FROM FavoriteNews WHERE title LIKE :query")
    fun searchNewsTitle(query: String): ArrayList<FavoriteNewsModel>

    @Insert
    fun insertNews(favoriteNewsModel: FavoriteNewsModel)

    @Delete
    fun delete(favoriteNewsModel: FavoriteNewsModel)
}