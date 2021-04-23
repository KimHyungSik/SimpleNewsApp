package com.example.newsapp.Retrofit

import com.example.newsapp.Utils.API
import com.example.newsapp.Utils.SEARCH_TYPE
import com.google.gson.JsonElement
import retrofit2.Call

object NewsRetrofitFactory {
    fun createRetrofit(searchType: SEARCH_TYPE,
                       country: String,
                       searchKeyword: String
    ): Call<JsonElement>? {
        val inNewsRetrofit : InNewsRetrofit? = NewsRetrofitClient.getClient(API.BASE_URL)?.create(InNewsRetrofit::class.java)
        return when(searchType){
            SEARCH_TYPE.EVERYTHING->{
                inNewsRetrofit?.defaultEverythinNews(searchKeywords= searchKeyword)
            }
            SEARCH_TYPE.TOPHEADLINES->{
                inNewsRetrofit?.defaultHeadLinesNews(country, searchKeyword)
            }
        }
    }
}