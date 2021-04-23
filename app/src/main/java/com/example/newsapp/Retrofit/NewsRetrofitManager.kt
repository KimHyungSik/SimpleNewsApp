package com.example.newsapp.Retrofit

import android.util.Log
import android.widget.Toast
import com.example.newsapp.Model.NewsModel
import com.example.newsapp.Utils.API.BASE_URL
import com.example.newsapp.Utils.RESPONSE_STATUIS
import com.example.newsapp.Utils.SEARCH_TYPE
import com.example.newsapp.Utils.Utility.TAG
import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class NewsRetrofitManager {

    companion object{
        val instance = NewsRetrofitManager()
    }



    fun searchNews(searchType: SEARCH_TYPE,searchKeywords : String?, country : String, completion: (RESPONSE_STATUIS, ArrayList<NewsModel>?) -> Unit){
        val keyword = searchKeywords ?: ""

        val call = NewsRetrofitFactory.createRetrofit(searchType, country, keyword)
        call?.enqueue(object : retrofit2.Callback<JsonElement>{
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

                   val total = body.get("totalResults").asInt

                   if(total == 0){
                       completion(RESPONSE_STATUIS.NO_CONTENT, null)
                   }else{

                       body.getAsJsonArray("articles").forEach {
                           val resultItemObject = it.asJsonObject
                           var title = ""
                           var description = ""
                           var author = ""
                           var publishedAt = ""
                           var url = ""
                           var urlToImage = ""

                           if(!resultItemObject.get("title").isJsonNull){
                               title = resultItemObject.get("title").asString
                           }

                           if(!resultItemObject.get("author").isJsonNull){
                               author = resultItemObject.get("author").asString
                           }

                           if(!resultItemObject.get("description").isJsonNull){
                               description = resultItemObject.get("description").asString
                           }

                           if(!resultItemObject.get("url").isJsonNull){
                               url = resultItemObject.get("url").asString
                           }

                           if(!resultItemObject.get("urlToImage").isJsonNull){
                               urlToImage = resultItemObject.get("urlToImage").asString
                           }

                           if(!resultItemObject.get("publishedAt").isJsonNull){
                               publishedAt =resultItemObject.get("publishedAt").asString
                           }


                           val newsModel = NewsModel(
                                   title,
                                   author,
                                   description,
                                   url,
                                   urlToImage,
                                   publishedAt
                           )
                           parsedNews.add(newsModel)
                       }
                       completion(RESPONSE_STATUIS.OK, parsedNews)
                   }
               }
            }

        })
    }

}