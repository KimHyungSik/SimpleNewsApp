package com.example.newsapp.Model

data class NewsModel(
    var title : String,
    var author : String?,
    var description : String,
    var url : String,
    var urlToImage : String,
    var publishedAt : String
){

}