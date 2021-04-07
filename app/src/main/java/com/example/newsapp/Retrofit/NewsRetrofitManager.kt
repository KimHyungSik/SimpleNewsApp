package com.example.newsapp.Retrofit

import android.util.Log
import com.example.newsapp.Model.NewsModel
import com.example.newsapp.Utils.API.BASE_URL
import com.example.newsapp.Utils.RESPONSE_STATUIS
import com.example.newsapp.Utils.Utility.TAG
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class NewsRetrofitManager {

    companion object{
        val instance = NewsRetrofitManager()
    }

    private val inNewsRetrofit : InNewsRetrofit? = NewsRetrofitClient.getClient(BASE_URL)?.create(InNewsRetrofit::class.java)

    fun searchHeadlinesNews(searchKeywords : String?, contry : String, completion: (RESPONSE_STATUIS, ArrayList<NewsModel>?) -> Unit){
        val keyword = searchKeywords ?: ""

        val call = inNewsRetrofit?.defaultHeadLinesNews(contry = contry, searchKeywords = keyword) ?: return
        call.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                completion(RESPONSE_STATUIS.ERROR, null)
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "onResponse: ${response.raw()}")

               response.body()?.let{
                   val parsedNews = ArrayList<NewsModel>()
                   
                   val body = it.asJsonObject
                   Log.d(TAG, "onResponse:let: $body")
                   completion(RESPONSE_STATUIS.OK, null)
               }
            }

        })
    }

}