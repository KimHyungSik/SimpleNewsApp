package com.example.newsapp.Room.favoriteList

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.Model.FavoriteNewsModel

@Dao
interface FavoriteNewsDao {

    @Query("SELECT * FROM FavoriteNewsModel ORDER BY newsId DESC")
    fun getAll(): List<FavoriteNewsModel>

    @Query("SELECT * FROM FavoriteNewsModel WHERE title LIKE :query")
    fun searchNewsTitle(query: String): List<FavoriteNewsModel>

    @Insert
    fun insertNews(favoriteNewsModel: FavoriteNewsModel)

    @Delete
    fun delete(favoriteNewsModel: FavoriteNewsModel)
}