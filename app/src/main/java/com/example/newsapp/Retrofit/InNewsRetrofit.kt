package com.example.newsapp.Retrofit

import com.example.newsapp.Utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InNewsRetrofit {

    @GET(API.BASE_URL_TOPHEADLINES)
    fun defaultHeadLinesNews(@Query("country") contry : String,
                             @Query("q") searchKeywords : String
                             ) : Call<JsonElement>

    @GET(API.BASE_URL_EVERYTHING)
    fun defaultEverythinNews(@Query("q") searchKeywords: String) :
            Call<JsonElement>
}