package com.example.newsapp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Model.NewsModel
import com.example.newsapp.Retrofit.NewsRetrofitManager
import com.example.newsapp.Utils.Utility
import com.example.newsapp.Utils.Utility.TAG

class NewsFragmentViewModel: ViewModel() {
    var _newsLiveData: MutableLiveData<ArrayList<NewsModel>> = MutableLiveData<ArrayList<NewsModel>>()

    fun getNews() {
        NewsRetrofitManager.instance.searchHeadlinesNews("", "kr", completion = { responsState, responseDataArrayList ->
            Log.d(TAG, "getNews: $responsState")
            Log.d(TAG, "getNews: $responseDataArrayList")
            _newsLiveData.postValue(responseDataArrayList)
        })
    }

}