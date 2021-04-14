package com.example.newsapp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Model.NewsModel
import com.example.newsapp.Retrofit.NewsRetrofitManager
import com.example.newsapp.Utils.Utility.TAG

class NewsFragmentViewModel: ViewModel() {
    var _newsLiveData: MutableLiveData<ArrayList<NewsModel>> = MutableLiveData<ArrayList<NewsModel>>()

    fun getNews() {
        NewsRetrofitManager.instance.searchHeadlinesNews("", "kr", completion = { responsState, responseDataArrayList ->
            _newsLiveData.postValue(responseDataArrayList)
        })
    }

    fun filter(query: String){
        NewsRetrofitManager.instance.searchHeadlinesNews(query, "kr", completion = { responsState, responseDataArrayList ->
            _newsLiveData.postValue(responseDataArrayList)
        })
    }

}