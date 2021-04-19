package com.example.newsapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteNews")
data class FavoriteNewsModel(
       @PrimaryKey(autoGenerate = true) val newsId: Int?,
       @ColumnInfo(name = "title") val title: String,
       @ColumnInfo(name = "author") val author: String?,
       @ColumnInfo(name = "description") val description: String?,
       @ColumnInfo(name = "url") val url: String?,
       @ColumnInfo(name = "urlToImage") val urlToImage: String,
       @ColumnInfo(name = "publishedAt") val publishedAt: String
)