package com.example.newsapp.Utils

import com.example.newsapp.Model.FavoriteNewsModel
import com.example.newsapp.Model.NewsModel

object NewsDataConverter {

    fun favoriteToNews(favoriteNewsModel: FavoriteNewsModel): NewsModel{
        return NewsModel(
                favoriteNewsModel.title,
                favoriteNewsModel.author,
                favoriteNewsModel.description.toString(),
                favoriteNewsModel.url.toString(),
                favoriteNewsModel.urlToImage,
                favoriteNewsModel.publishedAt
        )
    }

    fun newsToFavorite(newsModel: NewsModel): FavoriteNewsModel{

        return FavoriteNewsModel(
                null,
                newsModel.title,
                newsModel.author,
                newsModel.description,
                newsModel.url,
                newsModel.urlToImage,
                newsModel.publishedAt
        )
    }
}